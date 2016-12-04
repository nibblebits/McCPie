package com.mccarthydaniel.McCPie;

import com.mccarthydaniel.McCPie.exceptions.InvalidRangeException;
import com.mccarthydaniel.McCPie.exceptions.NoColoursAvailableException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * This is the actual graphical pie chart class, add this to a JFrame, or JPanel to see the McCPie chart.
 * If you have an IDE such as netbeans, you may also add the McCPie class to your toolbox pallet, allowing for an easy
 * click and drag of the component.
 * 
 * @author Daniel McCarthy
 */
public class McCPie extends JComponent {

    private static final int TEXT_BORDER_X_PERCENTAGE = 60;
    private static final int TEXT_BORDER_Y_PERCENTAGE = 10;
    private static final int TEXT_BORDER_WIDTH_PERCENTAGE = 30;
    private static final int TEXT_BORDER_HEIGHT_PERCENTAGE = 20;
    private final ArrayList<Slice> slices;
    private Font title_font = new Font("Serif", Font.BOLD, 1);
    private Font slice_name_font = new Font("Serif", Font.BOLD, 1);
    private final ArrayList<Color> available_colours;
    private DecimalFormat df = new DecimalFormat("#.##");
    private double value_sum;
    private Color border_colour;
    private String title;
    private boolean show_percentages;

    public McCPie() {
        this.slices = new ArrayList<Slice>();
        this.available_colours = new ArrayList<Color>();
        this.value_sum = 0;
        this.border_colour = new Color(156, 93, 82);
        this.show_percentages = false;
        this.title = "";
        this.setup_available_colours();
    }

    /* 
    * Sets up all available standard solid colours in the system.
    * These will be used later on to ensure that a unique colour may be returned
    * if desired.
    */
    private void setup_available_colours() {
        this.available_colours.add(Color.RED);
        this.available_colours.add(Color.GREEN);
        this.available_colours.add(Color.PINK);
        this.available_colours.add(Color.BLUE);
        this.available_colours.add(Color.ORANGE);
        this.available_colours.add(Color.WHITE);
        this.available_colours.add(Color.CYAN);
        this.available_colours.add(Color.YELLOW);
        this.available_colours.add(Color.MAGENTA);
    }

    /**
     * Returns the current version of the McCPie chart.
     * @return {@link String} containing version name
    */
    public String getVersion() {
        return Config.version_name;
    }

     /**
     * Sets the current percentage format.
     * The percentage format shows how much of a percentage to show should the system be configured
     * to show percentages, you can show percentages with the {@link #showPercentages} method. These
     * percentages are the calculated percentages of the slices for the pie chart.
     * <p>
     * The percentage format is handled with the {@link DecimalFormat} class, the default percentage format is set to "#.##"
     * </p>
     * @param percentage_format The string representation for the percentage format
    */
    public void setPercentageFormat(String percentage_format) {
        this.df = new DecimalFormat(percentage_format);
    }

    /**
     * Returns a random colour.
     * <p>
     * Attempts to return a unique random solid colour based on the available solid colours setup in the system, if no unique available solid colours can be
     * found it will return a random RGB colour that is not guaranteed to be unique.
     * </p>
     * @return {@link Color} object that represents a random colour.
     */
    public Color getRandomColour() {
        if (this.available_colours.isEmpty())
        {
            return getRandomRGBColour();
        }
        
        try {
            return getRandomAvailableSolidColour();
        } catch (NoColoursAvailableException ex) {
            // Do not need to do anything as this will never throw.
        }
        
        return null;
    }
    
    /**
     * Returns a random RGB Colour 
     * @return {@link Color} object that represents a random RGB colour.
     */
    public Color getRandomRGBColour() {
        Random random = new Random();
        int R = random.nextInt(255);
        int G = random.nextInt(255);
        int B = random.nextInt(255);
        return new Color(R, G, B);
    }

    /** 
     * Returns a random solid colour that is available and not yet used or throws a {@link NoColoursAvailableException} when no more
     * available solid colours exist.
     * 
     * @return {@link Color} object that represents a random solid colour.
     * @throws NoColoursAvailableException will throw when no more unique solid colours are available
     */
    public Color getRandomAvailableSolidColour() throws NoColoursAvailableException {
        if (this.available_colours.isEmpty()) {
            throw new NoColoursAvailableException("No more random colours are available, please select manually.");
        }
        Random rand = new Random();
        int index = rand.nextInt(this.available_colours.size());
        Color selected_colour = this.available_colours.get(index);
        this.available_colours.remove(index);
        return selected_colour;
    }

    /**
     * Configures the system to show the percentages for the size of the pie slices on the pie chart or not.
     * <p>
     * <b>Percentage example</b> <i>if you had two slices of equal value then they would both have a percentage of 50% and both take up half of the pie chart.</i>
     * <p>
     * <i>By default percentages will not be shown.</i>
     * </p>
     * @param show_percentages set to true if the system should show percentages on the pie chart.
     */
    public void showPercentages(boolean show_percentages) {
        this.show_percentages = show_percentages;
    }

    /**
     * Sets the title of the pie chart which will be displayed directly below it.
     * @param title the title the pie chart should have
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the title of the pie chart.
     * @return the title of the pie chart
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the colour of the border that shows the pie chart slice names.
     * <p>
     * <i>By default the border colour is brown</i>
     * </p>
     * @param colour the Colour to set the border to.
     */
    public void setBorderColour(Color colour) {
        this.border_colour = colour;
    }

    /**
     * Returns the colour of the border that shows the pie chart slice names.
     * @return the colour of the border
     */
    public Color getBorderColour() {
        return this.border_colour;
    }

    /**
     * Cleans the pie chart erasing all of its slices as if this was a new instance of {@link McCPie}
     * <p>
     * <i>This method should be called when all pie slices are now irrelevant and you wish to add new pie slices</i>
     * </p>
     */
    public void clean() {
        this.slices.clear();
        this.available_colours.clear();
        this.setup_available_colours();
        this.value_sum = 0;
        repaint();
    }

    /**
     * Adds a new slice to the pie chart
     * @param slice The pie chart slice to add to the pie chart
     */
    public void addSlice(Slice slice) {
        this.slices.add(slice);
        this.value_sum += slice.getValue();
        repaint();
    }

    /**
     * Create and adds slices by an array of <b>String[]</b> which represents slice names and an array of <b>double[]</b> which represents slice values
     * <p>
     * <i>The length of both the <b>String[]</b> array and the <b>double[]</b> array must match in length as each element in both of the arrays will
     * create one slice</i>
     * </p>
     * @param names The array of pie slice names
     * @param values The array of pie slice values
     * @throws InvalidRangeException should the length of "names" and the length of "values" not match
     */
    public void addSlicesByArray(String[] names, double[] values) throws InvalidRangeException {
        addSlicesByArray(names, values, null);
    }

    /**
     * Creates and adds slices by an array of <b>String[]</b> which represents slice names, an array of <b>double[]</b> which represents slice values 
     * and finally an array of <b>Color[]</b> which represents the colours for the slices.
     * 
     * @param names The array of pie slice names
     * @param values The array of pie slice values
     * @param colours The array of pie slice colours.
     * @throws InvalidRangeException should the length of "names", "values" and "colours" not match.
     */
    public void addSlicesByArray(String[] names, double[] values, Color[] colours) throws InvalidRangeException {
        if (names.length != values.length) {
            throw new InvalidRangeException("The names array and values array differ in length");
        }

        if (colours != null) {
            throw new InvalidRangeException("The colours array length differs from the array and values array");
        }

        for (int i = 0; i < names.length; i++) {
            Slice slice = new Slice();
            slice.setName(names[i]);
            slice.setValue(values[i]);
            if (colours != null) {
                slice.setColour(colours[i]);
            } else {
                slice.setColour(getRandomColour());
            }
            addSlice(slice);
        }
    }

    /* Special thanks to: http://www.sumtotalz.com/TotalAppsWorks/PieChart/Pie_Chart.htm for pointing me in the
    right direction of the drawing of the actual chart. All logic is my own hand with a little help from BBC Bitesize. */
    /**
     * Paints the pie chart
     * <p>
     * <i>
     *  You will not ever need to call this method manually as it is handled by java's "repaint" method, which is called automatically
     * by {@link McCPie}
     * </i>
     * </p>
     * @param g the graphics object to paint to.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int c_width = this.getWidth();
        int c_height = this.getHeight();

        int arc_size_w = (int) (c_width / 2);
        int arc_size_h = (int) (c_height / 1.3);

        float title_font_size = 10 * arc_size_w / 100;
        int title_pos_x = arc_size_w / 2 / 2;
        int title_pos_y = (int) (arc_size_h + title_font_size);
        this.title_font = this.title_font.deriveFont(title_font_size);

        float slice_name_font_size = 7 * arc_size_w / 100;
        this.slice_name_font = this.slice_name_font.deriveFont(slice_name_font_size);

        int text_border_x = McCPie.TEXT_BORDER_X_PERCENTAGE * c_width / 100;
        int text_border_y = McCPie.TEXT_BORDER_Y_PERCENTAGE * c_height / 100;
        int text_border_width = McCPie.TEXT_BORDER_WIDTH_PERCENTAGE * c_width / 100;
        int text_border_height = (int) ((McCPie.TEXT_BORDER_HEIGHT_PERCENTAGE * c_height / 100) + (this.slices.size() * slice_name_font_size));

        int last_angle = 0;
        int current_text_y = 0;

        g.setColor(this.border_colour);
        g.fillRect(text_border_x, text_border_y, text_border_width, text_border_height);

        for (Slice slice : this.slices) {
            int angle = (int) Math.round((slice.getValue() / this.value_sum) * 360);
            int s_x = (int) (text_border_x);
            int s_y = (int) (text_border_y + current_text_y + slice_name_font_size);
            Color slice_colour = slice.getColour();
            g.setColor(slice_colour);
            g.fillArc(0, 0, arc_size_w, arc_size_h, last_angle, angle);
            g.setFont(this.slice_name_font);

            String slice_str = slice.getName();
            if (this.show_percentages) {
                double percentage = (slice.getValue() * 100) / this.value_sum;
                slice_str += " : " + df.format(percentage) + "%";
            }
            g.drawString(slice_str, s_x, s_y);
            last_angle += angle;
            current_text_y += (slice_name_font_size);
        }

        g.setFont(this.title_font);
        g.setColor(Color.BLACK);
        g.drawString(this.title, title_pos_x, title_pos_y);

    }
}
