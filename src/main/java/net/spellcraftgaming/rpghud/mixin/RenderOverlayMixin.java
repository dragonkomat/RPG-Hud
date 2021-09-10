package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.RenderOverlay;

@Mixin(InGameHud.class)
public class RenderOverlayMixin extends DrawableHelper {
    private int ticks;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        ++this.ticks;
        ;
    }

// TODO:
//    @Inject(at = @At("HEAD"), method = "renderStatusBars", cancellable = true)
//    private void renderStatusBars(CallbackInfo info) {
//        info.cancel();
//    }

    @Inject(at = @At("HEAD"), method = "renderHotbar", cancellable = true)
    private void renderHotbar(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.HOTBAR))
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderMountJumpBar", cancellable = true)
    private void renderMountJumpBar(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.JUMP_BAR))
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderExperienceBar", cancellable = true)
    private void renderExperienceBar(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.EXPERIENCE))
            info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderMountHealth", cancellable = true)
    private void renderMountHealth(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.HEALTH_MOUNT))
            info.cancel();
    }
    
    @Inject(at = @At("HEAD"), method = "renderStatusEffectOverlay", cancellable = true)
    private void renderStatusEffectOverlay(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.STATUS_EFFECTS))
            info.cancel();
    }
}
