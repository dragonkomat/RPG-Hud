package net.spellcraftgaming.rpghud.gui.hud.element.extended;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public class HudElementWidgetExtended extends HudElement {

	public HudElementWidgetExtended() {
		super(HudElementType.WIDGET, 0, 0, 0, 0, true);
	}

	@Override
	public boolean checkConditions() {
		return this.mc.interactionManager.hasStatusBars();
	}

	@Override
	public void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, INTERFACE);
		int posY = this.settings.getPositionValue(Settings.widget_position)[1];
		gui.drawTexture(ms,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 50 : 26), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 4 : 0), 0, 35, 114, 44);
		if (this.mc.player.getVehicle() instanceof LivingEntity) {
			gui.drawTexture(ms,posX + (this.settings.getBoolValue(Settings.render_player_face) ? 51 : 23), posY + (this.settings.getBoolValue(Settings.render_player_face) ? 44 : 39), 164, 0, 92, 20);
		}

		int facePosX = this.settings.getPositionValue(Settings.face_position)[0];
		int facePosY = this.settings.getPositionValue(Settings.face_position)[1];
		if (this.settings.getBoolValue(Settings.render_player_face)) {
			gui.drawTexture(ms,posX + facePosX, posY + facePosY, 114, 0, 50, 50);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, getPlayerSkin(this.mc.player));			
			ms.scale(0.5f, 0.5f, 0.5f);
			gui.drawTexture(ms,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 32, 32, 32, 32);
			gui.drawTexture(ms,posX * 2 + 34 + facePosX * 2, posY * 2 + 34 + facePosY * 2, 160, 32, 32, 32);
			ms.scale(2.0f, 2.0f, 2.0f);
			RenderSystem.setShaderTexture(0, AbstractParentElement.GUI_ICONS_TEXTURE);
		} else {
			gui.drawTexture(ms,posX, 3, 214, 20, 26, 38);
			RenderSystem.setShaderTexture(0, AbstractParentElement.GUI_ICONS_TEXTURE);
		}
	}

}
