package slimeknights.tconstruct.library.client.renderer.font;

import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.Util;

import java.util.function.UnaryOperator;

import static java.awt.Color.HSBtoRGB;

// TODO: extract?
public class CustomFontColor {
  public static final Color MAX = valueToColor(1, 1);
  private static final UnaryOperator<Style> APPLY_MAX = style -> style.setColor(MAX);

  private CustomFontColor() {}

  /**
   * Takes a value between 0.0 and 1.0.
   * Returns a color between red and green, depending on the value. 1.0 is green.
   * If the value goes above 1.0 it continues along the color spectrum.
   */
  public static Color valueToColor(float value, float max) {
    // 0.0 -> 0 = red
    // 1.0 -> 1/3 = green
    // 1.5 -> 1/2 = aqua
    float v = value / max / 3;
    return Color.func_240743_a_(HSBtoRGB(v, 0.01f, 0.5f));
  }

  public static ITextComponent formatPartialAmount(int value, int max) {
    return new StringTextComponent(Util.df.format(value))
      .modifyStyle(style -> style.setColor(CustomFontColor.valueToColor(value, max)))
      .append(new StringTextComponent("/").mergeStyle(TextFormatting.GRAY))
      .append(new StringTextComponent(Util.df.format(max)).modifyStyle(APPLY_MAX));
  }
}
