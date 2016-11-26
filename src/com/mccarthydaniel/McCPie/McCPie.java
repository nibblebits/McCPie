package com.mccarthydaniel.McCPie;

import com.mccarthydaniel.McCPie.exceptions.NoColoursAvailableException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author Daniel McCarthy
 */
public class McCPie extends JComponent {

    private static final int TEXT_BORDER_X_PERCENTAGE = 60;
    private static final int TEXT_BORDER_Y_PERCENTAGE = 10;
    private static final int TEXT_BORDER_WIDTH_PERCENTAGE = 30;
    private static final int TEXT_BORDER_HEIGHT_PERCENTAGE = 20;
    private static final int TEXT_START_X = 20;
    private static final int TEXT_START_Y = 20;
    private static final int TEXT_PADDING = 20;
    private final ArrayList<Slice> slices;
    private Font title_font = new Font("Serif", Font.BOLD, 1);
    private Font slice_name_font = new Font("Serif", Font.BOLD, 1);
    private ArrayList<Color> available_colours;
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

    public String getVersion() {
        return Config.version_name;
    }

    public void setPercentageFormat(String percentage_format) {
        this.df = new DecimalFormat(percentage_format);
    }

    public Color getRandomRGBColour() {
        Random random = new Random();
        int R = random.nextInt(255);
        int G = random.nextInt(255);
        int B = random.nextInt(255);
        return new Color(R, G, B);
    }

    public void showPercentages(boolean show_percentages) {
        this.show_percentages = show_percentages;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setBorderColour(Color colour) {
        this.border_colour = colour;
    }

    public Color getBorderColour() {
        return this.border_colour;
    }

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

    public void clean() {
        this.slices.clear();
        this.available_colours.clear();
        this.setup_available_colours();
        this.value_sum = 0;
        repaint();
    }

    public void addSlice(Slice slice) {
        this.slices.add(slice);
        this.value_sum += slice.getValue();
        repaint();
    }

    /* Special thanks to: http://www.sumtotalz.com/TotalAppsWorks/PieChart/Pie_Chart.htm for pointing me in the
    right direction of the drawing of the actual chart. All logic is my own hand with a little help from BBC Bitesize. */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int c_width = this.getWidth();
        int c_height = this.getHeight();

        int arc_size_w = c_width / 2;
        int arc_size_h = c_height / 2;

        float title_font_size = 10 * arc_size_w / 100;
        int title_pos_x = arc_size_w / 2 / 2;
        int title_pos_y = (int) (arc_size_h + title_font_size);
        this.title_font = this.title_font.deriveFont(title_font_size);

        float slice_name_font_size = 5 * arc_size_w / 100;
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
