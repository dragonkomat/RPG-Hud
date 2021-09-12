package net.spellcraftgaming.rpghud.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
//import net.minecraft.client.util.math.MatrixStack;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.main.RenderOverlay;

@Mixin(InGameHud.class)
public class RenderOverlayMixin extends DrawableHelper {

    // TODO: Following redirection causes exception.
    //   Caused by: org.spongepowered.asm.mixin.injection.throwables.InjectionError: Critical injection failure:
    //   Redirector onDrawTexture(Lnet/minecraft/class_4587;IIIIII)V in rpg-hud.mixins.json:RenderOverlayMixin failed injection check, (0/1) succeeded. Scanned 1 target(s). Using refmap [1.17.1][Fabric]RPG-HUD-refmap.json

    //@Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawableHelper;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
    //private void onDrawTexture(DrawableHelper obj, MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
    //    obj.drawTexture(matrices, x, y, u, v, width, height);
    //}

    @Inject(at = @At("HEAD"), method = "renderHealthBar", cancellable = true)
    private void renderHealthBar(CallbackInfo info) {
        if(!RenderOverlay.shouldRenderVanilla(HudElementType.HEALTH))
            info.cancel();
    }

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
