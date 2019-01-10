/*
 * Copyright 2016 Longri
 * Copyright 2016-2017 devemux86
 * Copyright 2017 nebular
 * Copyright 2018 Gustl22
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.oscim.ios.backend;

import org.oscim.backend.canvas.Paint;
import org.robovm.apple.coregraphics.CGAffineTransform;
import org.robovm.apple.coregraphics.CGBitmapContext;
import org.robovm.apple.coregraphics.CGBlendMode;
import org.robovm.apple.coregraphics.CGLineCap;
import org.robovm.apple.coregraphics.CGLineJoin;
import org.robovm.apple.coretext.CTFont;
import org.robovm.apple.coretext.CTLine;
import org.robovm.apple.foundation.NSAttributedString;
import org.robovm.apple.uikit.NSAttributedStringAttributes;
import org.robovm.apple.uikit.UIColor;
import org.robovm.apple.uikit.UIFont;
import org.robovm.apple.uikit.UIFontWeight;

import java.util.HashMap;

/**
 * iOS specific implementation of {@link Paint}.
 */
public class IosPaint implements Paint {

    private static CGLineCap getLineCap(Cap cap) {
        switch (cap) {
            case BUTT:
                return CGLineCap.Butt;
            case ROUND:
                return CGLineCap.Round;
            case SQUARE:
                return CGLineCap.Square;
        }
        return CGLineCap.Butt;
    }

    private static CGLineJoin getLineJoin(Join join) {
        switch (join) {
            case MITER:
                return CGLineJoin.Miter;
            case ROUND:
                return CGLineJoin.Round;
            case BEVEL:
                return CGLineJoin.Bevel;
        }
        return CGLineJoin.Miter;
    }

    private Align align;
    private final NSAttributedStringAttributes attribs = new NSAttributedStringAttributes();
    private CGLineCap cap = CGLineCap.Butt;
    private CGLineJoin join = CGLineJoin.Miter;
    private Style style;
    private float textSize;
    private FontFamily fontFamily;
    private FontStyle fontStyle;
    private int colorInt;
    private int strokeColorInt;
    private CTLine ctLine;
    private boolean ctLineIsDirty = true;
    private String lastText = "";
    private float descent;
    private float fontHeight;
    private static final HashMap<String, UIFont> fontHashMap = new HashMap<>();

    float strokeWidth;

    @Override
    public int getColor() {
        return this.colorInt;
    }

    @Override
    public void setColor(int color) {
        if (colorInt == color) return;
        this.colorInt = color;
        synchronized (attribs) {
            attribs.setForegroundColor(getUiColor(color));
        }
        ctLineIsDirty = true;
    }

    public void setStrokeColor(int color) {
        if (strokeColorInt == color) return;
        this.strokeColorInt = color;
        synchronized (attribs) {
            attribs.setStrokeColor(getUiColor(color));
        }
        ctLineIsDirty = true;
    }

    private UIColor getUiColor(int color) {
        float colorA = ((color & 0xff000000) >>> 24) / 255f;
        float colorR = ((color & 0xff0000) >>> 16) / 255f;
        float colorG = ((color & 0xff00) >>> 8) / 255f;
        float colorB = (color & 0xff) / 255f;
        return new UIColor(colorR, colorG, colorB, colorA);
    }

    public CGLineCap getIosStrokeCap() {
        return this.cap;
    }

    @Override
    public void setStrokeCap(Cap cap) {
        this.cap = getLineCap(cap);
    }

    public CGLineJoin getIosStrokeJoin() {
        return this.join;
    }

    @Override
    public void setStrokeJoin(Join join) {
        this.join = getLineJoin(join);
    }

    @Override
    public void setStrokeWidth(float width) {
        if (this.strokeWidth == width) return;
        this.strokeWidth = width;
        this.ctLineIsDirty = true;
    }

    @Override
    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public void setTextAlign(Align align) {
        // TODO never read
        this.align = align;
    }

    @Override
    public void setTextSize(float textSize) {
        if (this.textSize != textSize) {
            this.textSize = textSize;
            createIosFont();
            ctLineIsDirty = true;
        }
    }

    @Override
    public void setTypeface(FontFamily fontFamily, FontStyle fontStyle) {
        if (fontFamily != this.fontFamily
                || fontStyle != this.fontStyle) {

            this.fontFamily = fontFamily;
            this.fontStyle = fontStyle;
            createIosFont();
            ctLineIsDirty = true;
        }
    }

    @Override
    public float measureText(String text) {
        if (ctLineIsDirty || !text.equals(lastText)) {
            ctLineIsDirty = true;
            createCTLine(text);
        }
        return (float) ctLine.getWidth();
    }

    private void createCTLine(String text) {
        if (ctLineIsDirty) {
            synchronized (attribs) {
                /*
                The sign of the value for NSStrokeWidthAttributeName is interpreted as a mode;
                it indicates whether the attributed string is to be filled, stroked, or both.
                Specifically, a zero value displays a fill only, while a positive value displays a stroke only.
                A negative value allows displaying both a fill and stroke.

                !!!!!
                NOTE: The value of NSStrokeWidthAttributeName is interpreted as a percentage of the font point size.
                */
                float strokeWidthPercent = (this.strokeWidth / this.textSize * 150);
                attribs.setStrokeWidth(strokeWidthPercent);

                NSAttributedString attributedString = new NSAttributedString(text, attribs);
                ctLine = CTLine.create(attributedString);
                attributedString.dispose();
            }
            lastText = text;
            ctLineIsDirty = false;
        }
    }

    private void createIosFont() {
        /*
          DEVICE_DEFAULT = [iOS == getDeviceDefault()], [Android == 'Roboto']
          MONOSPACE      = [iOS == 'Courier'], [Android == 'Droid Sans Mono']
          SANS_SERIF     = [iOS == 'HelveticaNeue'], [Android == 'Droid Sans']
          SERIF          = [iOS == 'Georgia'], [Android == 'Droid Serif']
         */

        // Better approach: use font descriptor
        // UIFontDescriptor fontD = font.getFontDescriptor();

        UIFontWeight weight = UIFontWeight.Regular;
        switch (this.fontFamily) {
            case DEFAULT_BOLD:
                weight = UIFontWeight.Bold;
                break;
            case MEDIUM:
                weight = UIFontWeight.Medium;
                break;
            case THIN:
                weight = UIFontWeight.Thin;
                break;
            case LIGHT:
                weight = UIFontWeight.Light;
                break;
            case BLACK:
                weight = UIFontWeight.Black;
                break;
//            case SANS_SERIF:
//                break;
//            case CONDENSED:
//                break;
//            case SERIF:
//                break;
//            case MONOSPACE:
//                break;
        }

        synchronized (attribs) {
            UIFont font = null;
            String fontname = null;

            switch (this.fontStyle) {
                case BOLD:
                    switch (this.fontFamily) {
                        case CONDENSED:
                            fontname = "HelveticaNeue-CondensedBold";
                            break;
                        case SERIF:
                            fontname = "Georgia-Bold";
                            break;
                        case MONOSPACE:
                            fontname = "CourierNewPS-BoldMT";
                            break;
                        default:
                            // Always bold
                            font = UIFont.getSystemFont(textSize, UIFontWeight.Bold);
                            break;
                    }
                    break;
                case ITALIC:
                    switch (this.fontFamily) {
                        case CONDENSED:
                            fontname = "HelveticaNeue-Italic";
                            break;
                        case SERIF:
                            fontname = "Georgia-Italic";
                            break;
                        case MONOSPACE:
                            fontname = "CourierNewPS-ItalicMT";
                            break;
                        default:
                            // Add differences in italic weight
                            font = UIFont.getItalicSystemFont(textSize);
                            break;
                    }
                    break;
                case BOLD_ITALIC:
                    switch (this.fontFamily) {
                        case CONDENSED:
                            fontname = "HelveticaNeue-BoldItalic";
                            break;
                        case SERIF:
                            fontname = "Georgia-BoldItalic";
                            break;
                        case MONOSPACE:
                            fontname = "CourierNewPS-BoldItalicMT";
                            break;
                        default:
                            // Always bold and italic
                            fontname = "HelveticaNeue-BoldItalic";
                            break;
                    }
                    break;
                default:
                    switch (this.fontFamily) {
                        case CONDENSED:
                            fontname = "HelveticaNeue";
                            // or if better "HelveticaNeue-CondensedBold", cond. regular not available
                            break;
                        case SERIF:
                            fontname = "Georgia";
                            break;
                        case MONOSPACE:
                            fontname = "CourierNewPSMT";
                            break;
                        default:
                            font = UIFont.getSystemFont(textSize, weight);
                            break;
                    }
                    break;
            }

            if (font == null) {
                String key = fontname + this.textSize;

                //try to get buffered font
                font = fontHashMap.get(key);

                if (font == null) {
                    CTFont ctFont = CTFont.create(fontname, this.textSize, CGAffineTransform.Identity());

                    descent = (float) ctFont.getDescent();
                    fontHeight = (float) ctFont.getBoundingBox().getHeight();

                    font = ctFont.as(UIFont.class);
                    fontHashMap.put(key, font);
                }
            }

            CTFont ctFont = font.as(CTFont.class);
            descent = (float) ctFont.getDescent();
            fontHeight = (float) ctFont.getBoundingBox().getHeight();

            attribs.setFont(font);
        }
    }

    public void drawLine(CGBitmapContext cgBitmapContext, String text, float x, float y) {
        if (ctLineIsDirty || !text.equals(lastText)) {
            ctLineIsDirty = true;
            createCTLine(text);
        }
        cgBitmapContext.saveGState();
        cgBitmapContext.setShouldAntialias(true);
        cgBitmapContext.setTextPosition(x, y + descent);
        cgBitmapContext.setBlendMode(CGBlendMode.Normal);

        ctLine.draw(cgBitmapContext);

        cgBitmapContext.restoreGState();
    }

    @Override
    public float getFontHeight() {
        return fontHeight;
    }

    @Override
    public float getFontDescent() {
        return descent;
    }

    @Override
    public float getStrokeWidth() {
        return strokeWidth;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public float getTextHeight(String text) {
        return this.fontHeight;
    }

    @Override
    public float getTextWidth(String text) {
        return measureText(text);
    }
}
