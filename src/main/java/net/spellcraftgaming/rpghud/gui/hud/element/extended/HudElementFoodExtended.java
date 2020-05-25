package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.spellcraftgaming.lib.GameData;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

public class HudElementFoodExtended extends HudElement {

	public HudElementFoodExtended() {
		super(HudElementType.FOOD, 0, 0, 0, 0, true);
		parent = HudElementType.WIDGET;
	}

	@Override
	public boolean checkConditions() {
		return this.mc.playerController.shouldDrawHUD();
	}

	@Override
	public void drawElement(Gui gui, float zLevel, float partialTicks) {
		int stamina = GameData.getPlayerFood();
		int foodMax = GameData.getPlayerMaxFood();
		int posX = (this.settings.getBoolValue(Settings.render_player_face) ? 49 : 25) + this.settings.getPositionValue(Settings.hunger_position)[0];
		int posY = (this.settings.getBoolValue(Settings.render_player_face) ? 22 : 18) + this.settings.getPositionValue(Settings.hunger_position)[1];
		ItemStack itemMain = GameData.getMainhand();
		ItemStack itemSec = GameData.getOffhand();

		if (GameData.doesPlayerNeedFood() && this.settings.getBoolValue(Settings.show_hunger_preview)) {
			float value = 0;
			if (itemMain != GameData.nullStack() && itemMain.getItem() instanceof ItemFood) {
				value = ((ItemFood) itemMain.getItem()).getHealAmount(itemMain);
			} else if (itemSec != GameData.nullStack() && itemSec.getItem() instanceof ItemFood) {
				value = ((ItemFood) itemSec.getItem()).getHealAmount(itemSec);
			}
			if (value > 0) {
				int bonusHunger = (int) (value + stamina);
				if (bonusHunger > foodMax)
					bonusHunger = foodMax;
				int colorPreview = offsetColor(this.settings.getIntValue(Settings.color_food), OFFSET_PREVIEW);
				drawCustomBar(posX, posY, 110, 12, bonusHunger / (double) foodMax * 100.0D, -1, -1, colorPreview, offsetColorPercent(colorPreview, OFFSET_PERCENT));
			}
		}

		if (GameData.isPlayerHungered()) {
			drawCustomBar(posX, posY, 110, 12, stamina / (double) foodMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_hunger), offsetColorPercent(this.settings.getIntValue(Settings.color_hunger), OFFSET_PERCENT));
		} else {
			drawCustomBar(posX, posY, 110, 12, stamina / (double) foodMax * 100.0D, -1, -1, this.settings.getIntValue(Settings.color_food), offsetColorPercent(this.settings.getIntValue(Settings.color_food), OFFSET_PERCENT));
		}
		String staminaString = stamina + "/" + foodMax;
		if (this.settings.getBoolValue(Settings.show_numbers_food))
			gui.drawCenteredString(GameData.getFontRenderer(), staminaString, posX + 55, posY + 2, -1);
	}

}
