package com.rvh.openoffice.parts.main.config;

import com.rvh.openoffice.parts.main.PartsCreator;
import com.rvh.openoffice.parts.main.config.styles.*;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

import static com.rvh.openoffice.parts.main.enums.NameSpaces.SPREAD_SHEET;


/**
 * http://www.datypic.com/sc/ooxml/s-sml-styles.xsd.html
 */
public class StylePartCreator extends PartsCreator<CellConfig> {

    private final ConfigCollection<Font> fontConfigCollection = new ConfigCollection<>();
    private final ConfigCollection<NumberFormat> numberFormatConfigCollection = new ConfigCollection<>();
    private final ConfigCollection<CellBorders> cellBordersConfigCollection = new ConfigCollection<>();
    private final ConfigCollection<Fill> fillConfigCollection = new ConfigCollection<>();
    private final ConfigCollection<Style> styleConfigCollection = new ConfigCollection<>();

    public StylePartCreator(ZipArchiveOutputStream zos, ConfigCollection<CellConfig> cellConfiguration) {
        super(zos, cellConfiguration);
    }

    /**
     * Based on the cell configuration we need to build 5 collections.
     * The font, numberformat, cellborders and fill collection should contain unique records.
     * The styleConfig collection contain references to the font, numberformat, cellborders and fill collection.
     * The styleconfig ID will be referenced in a cell to apply the format via the xfId of the cellconfig.
     * The result is that there may be multiple different Cellconfiguration but they reference the same
     * Styleconfig
     *
     * @throws XMLStreamException
     * @throws IOException
     */
    @Override
    public void createPart() throws XMLStreamException, IOException {

        createHeader("xl/styles.xml");

        //create body

        //build collections
        for (CellConfig cellConfig : configCollection.getConfigs()) {

            Style newOrExistingStyle = getStyle(cellConfig);

            //set the ID of the Style Collection to the cellConfig.
            cellConfig.setXfId(newOrExistingStyle.getId());

        }


        //write collections
        writeFonts();
        writeNumberFormat();
        writeAllCellBorders();
        writeFills();

        createFooter();

    }

    private void writeFills() throws XMLStreamException {

        List<Fill> fills = fillConfigCollection.getConfigs();

        if (fills != null && !fills.isEmpty()) {
            xsw.writeStartElement("fills");
            xsw.writeAttribute("count", String.valueOf(fills.size()));
            for (Fill fill : fills) {
                writeSingleFill(fill);
            }
            xsw.writeEndElement();//fills
        }

    }

    /**
     * Writes the fill configuration. Only one fill can be configured.
     * If both fills are configured then Gradient fill takes precedence
     * @param fill
     * @throws XMLStreamException
     */
    private void writeSingleFill(Fill fill) throws XMLStreamException {

        xsw.writeStartElement("fill");

        if (fill.getGradientFill() != null) {
             writeGradientFill(fill.getGradientFill());
        } else {
            writePatternFill(fill.getPatternFill());
        }

        xsw.writeEndElement();//fill


    }

    private void writePatternFill(PatternFill patternFill) throws XMLStreamException {

        xsw.writeStartElement("patternFill");
        if (patternFill.getPatternType()!=null)
            xsw.writeAttribute("patternType", patternFill.getPatternType().getValue());
        if(patternFill.getBackGroundColor()!=null)
            writeColor(patternFill.getBackGroundColor());
        if(patternFill.getForeGroundColor()!=null)
            writeColor(patternFill.getForeGroundColor());
        xsw.writeEndElement();//patternFill

    }

    private void writeGradientFill(GradientFill gradientFill) throws XMLStreamException {

        xsw.writeStartElement("gradientFill");
        if (gradientFill.getGradientType()!=null)
            xsw.writeAttribute("type", gradientFill.getGradientType().getValue());
        if (gradientFill.getDegree() !=0)
            xsw.writeAttribute("degree", String.valueOf(gradientFill.getDegree()));
        if (gradientFill.getLeft()!= 0)
            xsw.writeAttribute("left", String.valueOf(gradientFill.getLeft()));
        if (gradientFill.getRight()!=0)
            xsw.writeAttribute("right", String.valueOf(gradientFill.getRight()));
        if (gradientFill.getBottom()!= 0)
            xsw.writeAttribute("bottom", String.valueOf(gradientFill.getBottom()));
        if (gradientFill.getTop()!=0)
            xsw.writeAttribute("top", String.valueOf(gradientFill.getTop()));

        //TODO: write GradientStop element (not in model yet.

        xsw.writeEndElement(); // gradientFill

    }

    private void writeAllCellBorders() throws XMLStreamException {

        List<CellBorders> cellBorders = cellBordersConfigCollection.getConfigs();

        if (cellBorders != null && !cellBorders.isEmpty()) {
            xsw.writeStartElement("borders");
            for (CellBorders cellBorder : cellBorders) {
                writeCellBorders(cellBorder);
            }
            xsw.writeEndElement();
        }


    }

    /**
     * writes all borders of a single cell configuration according to defined sequence
     *
     * @param cellBorder
     * @throws XMLStreamException
     */
    private void writeCellBorders(CellBorders cellBorder) throws XMLStreamException {

        xsw.writeStartElement("border");

        writeSingleCellBorder("left", cellBorder.getLeft());
        writeSingleCellBorder("right", cellBorder.getRight());
        writeSingleCellBorder("top", cellBorder.getTop());
        writeSingleCellBorder("bottom", cellBorder.getBottom());
        writeSingleCellBorder("diagonal", cellBorder.getDiagonal());
        writeSingleCellBorder("vertical", cellBorder.getVertical());
        writeSingleCellBorder("horizontal", cellBorder.getHorizontal());

        xsw.writeEndElement();//border

    }

    /**
     * writes a single border of a single cell configuration
     *
     * @param borderElementName
     * @param border
     * @throws XMLStreamException
     */
    private void writeSingleCellBorder(String borderElementName, Border border) throws XMLStreamException {

        if (border != null && borderElementName != null && !borderElementName.isEmpty()) {
            xsw.writeStartElement(borderElementName);
            if (border.getStyle()!=null)
                xsw.writeAttribute("style", border.getStyle().getValue());
            if (border.getColor()!=null)
                writeColor(border.getColor());
            xsw.writeEndElement();
        }

    }

    private void writeNumberFormat() throws XMLStreamException {

        List<NumberFormat> numberFormats = numberFormatConfigCollection.getConfigs();

        if (numberFormats != null && !numberFormats.isEmpty()) {
            xsw.writeStartElement("numFmts");
            xsw.writeAttribute("count", String.valueOf(numberFormats.size()));
            for (NumberFormat format : numberFormats) {
                writeSingleNumberFormat(format);
            }
            xsw.writeEndElement();
        }

    }

    private void writeSingleNumberFormat(NumberFormat format) throws XMLStreamException {

        xsw.writeStartElement("numFmt");

        xsw.writeAttribute("numFmtId", format.getId());
        xsw.writeAttribute("formatCode", format.getFormatCode());

        xsw.writeEndElement();

    }

    /**
     * writes the <Fonts></Fonts> collection to the styles.xml
     * Each record in the collection results in a <Font></Font>
     *
     * @throws XMLStreamException
     */
    private void writeFonts() throws XMLStreamException {

        List<Font> fonts = fontConfigCollection.getConfigs();

        if (fonts != null && !fonts.isEmpty()) {
            xsw.writeStartElement("fonts");
            xsw.writeAttribute("count", String.valueOf(fonts.size()));
            for (Font font : fonts) {
                writeSingleFont(font);
            }
            xsw.writeEndElement();//Fonts
        }

    }

    /**
     * Writes the <Font></Font> element with all it's children
     *
     * @param font
     * @throws XMLStreamException
     */
    private void writeSingleFont(Font font) throws XMLStreamException {

        xsw.writeStartElement("font");

        if (font.getName() != null && !font.getName().isEmpty())
            writeEmptyElementWithAttribute("name", font.getName());
        if (font.getCharSet() > 0)
            writeEmptyElementWithAttribute("charset", String.valueOf(font.getCharSet()));
        if (font.getFamily() > 0)
            writeEmptyElementWithAttribute("family", String.valueOf(font.getFamily()));
        if (font.getColor() != null)
            writeColor(font.getColor());
        if (font.getFontSize() > 0)
            writeEmptyElementWithAttribute("sz", String.valueOf(font.getFontSize()));
        if (font.getUnderLine() != null)
            writeEmptyElementWithAttribute("u", font.getUnderLine().getValue());
        if (font.getFontScheme() != null)
            writeEmptyElementWithAttribute("scheme", font.getFontScheme().getValue());

        writeEmptyElementWithAttribute("b", String.valueOf(font.isBold()));
        writeEmptyElementWithAttribute("i", String.valueOf(font.isItalic()));
        writeEmptyElementWithAttribute("strike", String.valueOf(font.isStrikeThrough()));
        writeEmptyElementWithAttribute("outline", String.valueOf(font.isOutLine()));
        writeEmptyElementWithAttribute("shadow", String.valueOf(font.isShadow()));
        writeEmptyElementWithAttribute("condense", String.valueOf(font.isCondense()));
        writeEmptyElementWithAttribute("extend", String.valueOf(font.isExtend()));

        xsw.writeEndElement();//Font

    }

    /**
     * writes the <Color attr.. attr.. /> element with all its attributes
     *
     * @param color
     * @throws XMLStreamException
     */
    private void writeColor(Color color) throws XMLStreamException {

        xsw.writeEmptyElement("color");
        if (!color.isAuto())
            xsw.writeAttribute("auto", String.valueOf(color.isAuto()));
        if (color.getIndexed() > 0)
            xsw.writeAttribute("indexed", String.valueOf(color.getIndexed()));
        if (color.getRgb() != null && !color.getRgb().isEmpty())
            xsw.writeAttribute("rgb", String.valueOf(color.getRgb()));
        if (color.getTheme() > 0)
            xsw.writeAttribute("theme", String.valueOf(color.getTheme()));
        if (color.getTint() > 0)
            xsw.writeAttribute("tint", String.valueOf(color.getTint()));

    }

    /**
     * Writes an empty xml element having a val attribute:
     * <ssml:name val="string"/>
     *
     * @param name
     * @param value
     * @throws XMLStreamException
     */
    private void writeEmptyElementWithAttribute(String name, String value) throws XMLStreamException {

        xsw.writeEmptyElement(name);
        xsw.writeAttribute("val", value);

    }

    /**
     * Creates a style based on the font, numberformat, border and fill configuration defined
     * in the cellConfig. Style is added to the collection if non-existing.
     *
     * @param cellConfig cell configuration which required to be verified
     * @return Style that is either created or already existed
     */
    private Style getStyle(CellConfig cellConfig) {

        List<Style> styleConfigs = styleConfigCollection.getConfigs();

        Style style = new Style(null, String.valueOf(styleConfigs.size()));

        // get font and verify if it exists in collection. If not, add it
        setFontId(cellConfig, style);
        // get numberFormat and verify if it exists in collection. If not, add it
        setNumberFormatId(cellConfig, style);
        // get CellBorders and verify if it exists in collection. If not, add it
        setBorderId(cellConfig, style);
        // get Fill and verify if it exists in collection. If not, add it
        setFillId(cellConfig, style);

        //add style to styleConfigs if it does not yet exist
        if (!styleConfigs.contains(style)) {
            styleConfigs.add(style);
        }

        //get the index of the defined style and return it
        int index = styleConfigs.indexOf(style);

        return styleConfigs.get(index);
    }

    /**
     * Verifies if the font defined in the cell configuration already exist in the
     * font collection. If not, then add the font
     * The index of the font in the font collection is set to the Style object
     *
     * @param cellConfig containing font configuration
     * @param style      enhanced with fontId
     */
    private void setFontId(CellConfig cellConfig, Style style) {
        String fontConfigId;
        List<Font> fonts = fontConfigCollection.getConfigs();
        Font cellFont = cellConfig.getFont();

        if (cellFont != null) {
            if (!fonts.contains(cellFont)) {
                fonts.add(cellFont);
            }
            fontConfigId = String.valueOf(fonts.indexOf(cellFont));

            style.setFontId(fontConfigId);
        }

    }

    /**
     * Verifies if the NumberFormat defined in the cell configuration already exist in the
     * NumberFormat collection. If not, then add the NumberFormat
     * The index of the NumberFormat in the NumberFormat collection is set to the Style object
     *
     * @param cellConfig containing NumberFormat configuration
     * @param style      enhanced with NumberFormatId
     */
    private void setNumberFormatId(CellConfig cellConfig, Style style) {
        String numberFormatId;
        List<NumberFormat> numberFormats = numberFormatConfigCollection.getConfigs();
        NumberFormat numberFormat = cellConfig.getNumberFormat();

        if (numberFormat != null) {
            if (!numberFormats.contains(numberFormat)) {
                numberFormats.add(numberFormat);
            }
            numberFormatId = String.valueOf(numberFormats.indexOf(numberFormat));

            style.setNumberFormatId(numberFormatId);
        }

    }

    /**
     * Verifies if the Border defined in the cell configuration already exist in the
     * Border collection. If not, then add the Border
     * The index of the Border in the Border collection is set to the Style object
     *
     * @param cellConfig containing Border configuration
     * @param style      enhanced with BorderId
     */
    private void setBorderId(CellConfig cellConfig, Style style) {
        String cellBordersId;
        List<CellBorders> cellBorders = cellBordersConfigCollection.getConfigs();
        CellBorders cellBorder = cellConfig.getCellBorders();

        if (cellBorder != null) {
            if (!cellBorders.contains(cellBorder)) {
                cellBorders.add(cellBorder);
            }
            cellBordersId = String.valueOf(cellBorders.indexOf(cellBorder));

            style.setCellBordersId(cellBordersId);
        }
    }

    /**
     * Verifies if the Fill defined in the cell configuration already exist in the
     * Fill collection. If not, then add the Fill
     * The index of the Fill in the Fill collection is set to the Style object
     *
     * @param cellConfig containing Fill configuration
     * @param style      enhanced with FillId
     */
    private void setFillId(CellConfig cellConfig, Style style) {
        String fillId;
        List<Fill> fills = fillConfigCollection.getConfigs();
        Fill fill = cellConfig.getFill();

        if (fill != null) {
            if (!fills.contains(fill)) {
                fills.add(fill);
            }
            fillId = String.valueOf(fills.indexOf(fill));

            style.setFillId(fillId);
        }
    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {

        zos.putArchiveEntry(new ZipArchiveEntry(name));
        xsw.writeStartDocument("UTF-8", "1.0");
        xsw.writeStartElement("styleSheet");
        xsw.writeDefaultNamespace(SPREAD_SHEET.getSchema());

    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {

        xsw.writeEndElement();//Styles
        xsw.flush();
        zos.closeArchiveEntry();

    }
}
