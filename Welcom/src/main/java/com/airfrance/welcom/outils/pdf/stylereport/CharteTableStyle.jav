package com.airfrance.welcom.outils.pdf.stylereport;

/*
* Copyright (c) 1996-1998, InetSoft Technology Corp, All Rights Reserved.
*
* The software and information contained herein are copyrighted and
* proprietary to InetSoft Technology Corp. This software is furnished
* pursuant to a written license agreement and may be used, copied,
* transmitted, and stored only in accordance with the terms of such
* license and with the inclusion of the above copyright notice. Please
* refer to the file "COPYRIGHT" for further copyright and licensing
* information. This software and information or any other copies
* thereof may not be provided or otherwise made available to any other
* person.
*/
import inetsoft.report.TableLens;
import inetsoft.report.style.TableStyle;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;

/**
* The FriendlyStyle class is a table style class. It can be used with other
* table lens classes to provide a visual style for table formatting and
* printing.
*
* @version 1.0, 5/23/98
* @auther InetSoft Technology Corp
*/
public class CharteTableStyle extends TableStyle {

    /**
     * 
     */
    private static final long serialVersionUID = -1965402820553906719L;

    /** bold */
    private Hashtable bold;

    /** Italique */
    private Hashtable italique;

    /** Taille 7 de la font */
    private static final int FONT_SIZE_7 = 7;

    /** Taille 5 de la font */
    private static final int FONT_SIZE_5 = 5;

    /**
    Required to extend the TableStyle class.
    * Create an empty style. The setTable() method must be called before
    * it can be used.
    */
    public CharteTableStyle() {
    }

    /**
    * Create a style to decorate the specified table.
    * @param table table lens.
    */
    public CharteTableStyle(final TableLens table) {
        super(table);
    }

    /**
    * Create a style to decorate the table.
    * @param tbl TableLens
    * @return a style lens.
    */
    protected TableLens createStyle(final TableLens tbl) {
        return new Style();
    }

    /**
     * Sets the bold.
     * @param pBold The bold to set
     */
    public void setBold(final Hashtable pBold) {
        this.bold = pBold;
    }

    /**
     * Sets the italique.
     * @param pItalique The italique to set
     */
    public void setItalique(final Hashtable pItalique) {
        this.italique = pItalique;
    }

    /**
    * Style lens.
    */
    class Style extends Transparent {

        /**
         * 
         */
        private static final long serialVersionUID = -7099077356819057256L;
        /** Connerie Check Style ! */
        private static final int COLOR_TETE_RED = 168;
        /** Connerie Check Style ! */
        private static final int COLOR_TETE_GREEN = 175;
        /** Connerie Check Style ! */
        private static final int COLOR_TETE_BLUE = 216;
        /** Connerie Check Style ! */
        private static final int COLOR_CORPS_RED = 229;
        /** Connerie Check Style ! */
        private static final int COLOR_CORPS_GREEN = 232;
        /** Connerie Check Style ! */
        private static final int COLOR_CORPS_BLUE = 248;
        /** Connerie Check Style ! */
        private static final int COLOR_CORPSCLAIR_RED = 245;
        /** Connerie Check Style ! */
        private static final int COLOR_CORPSCLAIR_GREEN = 245;
        /** Connerie Check Style ! */
        private static final int COLOR_CORPSCLAIR_BLUE = 252;

        /** Constante de couleur : TRTETE charte*/
        private final Color cTrTete = new Color(COLOR_TETE_RED, COLOR_TETE_GREEN, COLOR_TETE_BLUE);
        /** Constante de couleur : TRCORPS charte*/
        private final Color cTrCorps = new Color(COLOR_CORPS_RED, COLOR_CORPS_GREEN, COLOR_CORPS_BLUE);
        /** Constante de couleur : TRCORPSCLAIR charte*/
        private final Color cTrCorpsClair = new Color(COLOR_CORPSCLAIR_RED, COLOR_CORPSCLAIR_GREEN, COLOR_CORPSCLAIR_BLUE);

        /**
        Transparent is defined in the TableStyle class, and
        implements every TableLens method to pass through the
        calls to the base table. It must be the base class of
        the inner style class.
        * Return the color for drawing the row border lines.
        * @param r row number.
        * @param c column number.
        * @return ruling color.
        */
        public Color getRowBorderColor(final int r, final int c) {
            return (r != lastRow()) ? Color.white : cTrTete;
        }

        /**
        * Return the color for drawing the column border lines.
        * @param r row number.
        * @param c column number.
        * @return ruling color.
        */
        public Color getColBorderColor(final int r, final int c) {
            return Color.white;
        }

        /**
        * Return the style for bottom border of the specified cell. The flag
        * must be one of the style options defined in the StyleConstants
        * class. If the row number is -1, it's checking the outside ruling
        * on the top.
        * @param r row number.
        * @param c column number.
        * @return ruling flag.
        */
        public int getRowBorder(final int r, final int c) {
            if (r == -1) {
                return NO_BORDER;
            } else if (r == lastRow()) {
                return THICK_LINE;
            } else {
                return THIN_LINE;
            }
        }

        /**
        * Return the style for right border of the specified row. The flag
        * must be one of the style options defined in the StyleConstants
        * class. If the column number is -1, it's checking the outside ruling
        * on the left.
        * @param r row number.
        * @param c column number.
        * @return ruling flag.
        */
        public int getColBorder(final int r, final int c) {
            return ((r == 0) && (c != -1) && (c != lastCol())) ? THIN_LINE : NO_BORDER;
        }

        /**
        * Return the per cell alignment.
        * @param r row number.
        * @param c column number.
        * @return cell alignment.
        */
        public int getAlignment(final int r, final int c) {
            return (H_LEFT | V_CENTER);
        }

        /**
        * Return the per cell font. Return null to use default font.
        * @param r row number.
        * @param c column number.
        * @return font for the specified cell.
        */
        public Font getFont(final int r, final int c) {
            Font font = new Font("Verdana", Font.PLAIN, FONT_SIZE_7);

            if (italique != null) {
                if (italique.get(new Integer(r)) != null) {
                    font = createFont(font, Font.ITALIC, FONT_SIZE_7);
                }
            }

            if (bold != null) {
                if (bold.get(new Integer(r)) != null) {
                    font = createFont(font, Font.BOLD, FONT_SIZE_5);
                }
            }

            if ((bold != null) && (italique != null)) {
                if ((bold.get(new Integer(r)) != null) && (italique.get(new Integer(r)) != null)) {
                    font = createFont(font, Font.BOLD | Font.ITALIC, FONT_SIZE_5);
                }
            }

            // apply special format to the first/last row/column
            if (isHeaderFooter(r, c)) {
                return createFont(font, Font.BOLD, FONT_SIZE_7);
            }

            /*Apply special font to the first/last row/column. We
            explicitly check if the special formatting should be
            applied.*/
            return font;
        }

        /**
        * apply special format to the first/last row/column
        * @param r row number.
        * @param c column number.
        * @return font for the specified cell.
        */
        private boolean isHeaderFooter(final int r, final int c) {
            return (isFormatFirstRow() && ((r == 0) || (r == lastRow()))) || 
                   (isFormatFirstCol() && ((c == 0) || (c == lastCol())));
        }

        /**
        * Return the per cell foreground color. Return null to use default
        * color.
        * @param r row number.
        * @param c column number.
        * @return foreground color for the specified cell.
        */
        public Color getForeground(final int r, final int c) {
            if (isFormatFirstRow() && (r == 0)) {
                return Color.white;
            }

            return Color.black;
        }

        /**
        * Return the per cell background color. Return null to use default
        * color.
        * @param r row number.
        * @param c column number.
        * @return background color for the specified cell.
        */
        public Color getBackground(final int r, final int c) {
            //trtete
            if (isFormatFirstRow() && (r == 0)) {
                return cTrTete;
            }

            //trcorps
            if ((r % 2) == 0) {
                return cTrCorps;
            }

            //trcorpsclair
            return cTrCorpsClair;

        }
    }
}