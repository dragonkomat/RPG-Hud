package net.spellcraftgaming.rpghud.gui.hud.element;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spellcraftgaming.rpghud.main.ModRPGHud;
import net.spellcraftgaming.rpghud.settings.Settings;

@Environment(EnvType.CLIENT)
public abstract class HudElement {

    /** The values of the color red */
    public static final int COLOR_RED = 0xC10000;

    /** The values of the color red */
    public static final int COLOR_PINK = 0xFF69B4;

    /** The values of the color red */
    public static final int COLOR_BROWN = 0x8b4513;

    /** The values of the color white */
    public static final int COLOR_WHITE = 0xF2F2F2;

    /** The values of the color white */
    public static final int COLOR_ORANGE = 0xFF8400;

    /** The values of the color green */
    public static final int COLOR_GREEN = 0x3BC200;

    /** The values of the color red */
    public static final int COLOR_PURPLE = 0xA400F0;

    /** The values of the color blue */
    public static final int COLOR_BLUE = 0x005BC2;

    /** The values of the color blue */
    public static final int COLOR_AQUA = 0x00FFFF;

    /** The value of the color black */
    public static final int COLOR_BLACK = 0x292929;

    /** The values of the color grey */
    public static final int COLOR_GREY = 0x8A8A8A;

    /** The values of the color yellow */
    public static final int COLOR_YELLOW = 0xEEEE00;

    /** The values of the default color */
    public static final int[] COLOR_DEFAULT = { 0x4C4C4C, 0x3D3D3D };

    /** ResourceLocation of the interface texture for the RPG-HUD */
    protected static final Identifier INTERFACE = new Identifier("rpghud:textures/interface.png");

    public static final int OFFSET_PERCENT = 25;

    public static final int OFFSET_PREVIEW = 0x5A5A5A;

    /** The x coordinate the element will be rendered at on the screen */
    protected int posX;
    /** The y coordinate the element will be rendered at on the screen */
    protected int posY;

    /**
     * The default x coordinate the element will be rendered at on the screen
     */
    protected final int defaultPosX;
    /**
     * The default y coordinate the element will be rendered at on the screen
     */
    protected final int defaultPosY;

    /** The width of this element */
    protected int elementWidth;
    /** The height of this element */
    protected int elementHeight;
    /** Whether this element can be moved */
    protected boolean moveable;
    /** The Type of this element */
    protected HudElementType type;

    /** The Minecraft instance */
    protected MinecraftClient mc;

    /** The Mod instance */
    protected ModRPGHud rpgHud;

    /** The Mod settings */
    protected Settings settings;

    protected double scale;
    protected double scaleInverted;

    public HudElementType parent;
    /**
     * Constructor
     * 
     * @param type
     *            The HudElementType of this element
     * @param posX
     *            The initial position of this element (not yet implemented)
     * @param posY
     *            The initial position of this element (not yet implemented)
     * @param width
     *            The width of this element (not yet implemented)
     * @param height
     *            The height of this element (not yet implemented)
     * @param moveable
     *            Whether this element should be allowed to be moved around
     */
    public HudElement(HudElementType type, int posX, int posY, int width, int height, boolean moveable) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.defaultPosX = posX;
        this.defaultPosY = posY;
        this.elementWidth = width;
        this.elementHeight = height;
        this.moveable = moveable;
        this.mc = MinecraftClient.getInstance();
        this.rpgHud = ModRPGHud.instance;
        this.settings = this.rpgHud.settings;
        this.scale = 1D;
        this.scaleInverted = 1D / this.scale;
        this.parent = type;
    }

    /**
     * Function called to draw this element on the screen
     */
    public void draw(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        this.drawElement(gui, ms, zLevel, partialTicks, scaledWidth, scaledHeight);
    }

    public abstract void drawElement(DrawableHelper gui, MatrixStack ms, float zLevel, float partialTicks, int scaledWidth, int scaledHeight);

    /**
     * Returns the x coordinate of this element
     * 
     * @return x coordinate
     */
    public int getPosX(int scaledWidth) {
        return this.posX;
    }

    /**
     * Returns the y coordinate of this element
     * 
     * @return y coordinate
     */
    public int getPosY(int scaledHeight) {
        return this.posY;
    }

    /**
     * Returns the width of this element
     * 
     * @return width
     */
    public int getWidth(int scaledWidth) {
        return this.elementWidth;
    }

    /**
     * Returns the height of this element
     * 
     * @return height
     */
    public int getHeight(int scaledHeight) {
        return this.elementHeight;
    }

    public double getScale() {
        return 1;
    }
    
    public double getInvertedScale() {
        return 1 / getScale();
    }
    /**
     * Returns whether this element can be moved or not
     * 
     * @return moveable
     */
    public boolean isMoveable() {
        return this.moveable;
    }

    /**
     * Returns the type of this element
     * 
     * @return type
     */
    public HudElementType getType() {
        return this.type;
    }

    /**
     * Sets the position of this element to posX and posY if they are valid
     * 
     * @param posX
     * @param posY
     * @return whether the position is valid or not
     */
    public boolean setPos(int posX, int posY) {
        boolean xValid = false;
        boolean yValid = false;
        if (posX >= 0 && posX < (this.mc.getWindow().getScaledWidth() - this.elementWidth)) {
            xValid = true;
        }
        if (posY >= 0 && posY < (this.mc.getWindow().getScaledHeight() - this.elementHeight)) {
            yValid = true;
        }
        if (xValid && yValid) {
            this.posX = posX;
            this.posY = posY;
        }
        return xValid && yValid;
    }

    /**
     * Resets the position of this element to it's default position
     */
    public void setPositionToDefault() {
        this.posX = this.defaultPosX;
        this.posY = this.defaultPosY;
    }

    /**
     * This function must return true when the element should be rendered<br>
     * For example:<br>
     * For the air element this should return true whenever the player is in
     * survival/adventure and under water
     */
    public boolean checkConditions() {
        return true;
    }

    /**
     * Draws a rectangle on the screen
     * 
     * @param posX
     *            the x positon on the screen
     * @param posY
     *            the y positon on the screen
     * @param width
     *            the width of the rectangle
     * @param height
     *            the height of the rectangle
     * @param color
     *            the color of the rectangle
     */
    public static void drawRect(int posX, int posY, int width, int height, int color) {
        if (color == -1)
            return;
        //float f3;
        //if (color <= 0xFFFFFF && color >= 0)
        //    f3 = 1.0F;
        //else
        //    f3 = (color >> 24 & 255) / 255.0F;
        //float f = (color >> 16 & 255) / 255.0F;
        //float f1 = (color >> 8 & 255) / 255.0F;
        //float f2 = (color & 255) / 255.0F;
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        //RenderSystem.color4f(f, f1, f2, f3);
        RenderSystem.disableDepthTest();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(DrawMode.QUADS, VertexFormats.POSITION);
        vertexbuffer.vertex(posX, (double) posY + height, 0.0D).next();
        vertexbuffer.vertex((double) posX + width, (double) posY + height, 0.0D).next();
        vertexbuffer.vertex((double) posX + width, posY, 0.0D).next();
        vertexbuffer.vertex(posX, posY, 0.0D).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        //RenderSystem.color3f(1f, 1f, 1f);
    }

    /**
     * Draw an outline on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the outline
     * @param height
     *            the height of the outline
     * @param color
     */
    protected static void drawOutline(int x, int y, int width, int height, int color) {
        drawRect(x, y, width, 1, color);
        drawRect(x, y, 1, height, color);
        drawRect(x + width - 1, y, 1, height, color);
        drawRect(x, y + height - 1, width, 1, color);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     */
    public static void drawCustomBar(int x, int y, int width, int height, double value, int colorBarLight, int colorBarDark) {
        drawCustomBar(x, y, width, height, value, HudElement.COLOR_DEFAULT[0], HudElement.COLOR_DEFAULT[1], colorBarLight, colorBarDark, true, 0x000000);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorGroundLight
     *            the color for the background (light)
     * @param colorGroundDark
     *            the color for the background (dark)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     */
    public static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark) {
        drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, 0x000000);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorGroundLight
     *            the color for the background (light)
     * @param colorGroundDark
     *            the color for the background (dark)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     * @param outlined
     *            whether this bar has an outline or not
     */
    public static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined) {
        drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, outlined, 0x000000);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorGroundLight
     *            the color for the background (light)
     * @param colorGroundDark
     *            the color for the background (dark)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     * @param colorOutline
     *            the color of the outline
     */
    public static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, int colorOutline) {
        drawCustomBar(x, y, width, height, value, colorGroundLight, colorGroundDark, colorBarLight, colorBarDark, true, colorOutline);
    }

    /**
     * Draws a bar on the screen
     * 
     * @param x
     *            the x position on the screen
     * @param y
     *            the y position on the screen
     * @param width
     *            the width of the bar (with outline)
     * @param height
     *            the height of the bar (with outline)
     * @param value
     *            the converted value of the bar (maxed at 100.0D)
     * @param colorGroundLight
     *            the color for the background (light)
     * @param colorGroundDark
     *            the color for the background (dark)
     * @param colorBarLight
     *            the color for the bar (light)
     * @param colorBarDark
     *            the color for the bar (dark
     * @param outlined
     *            whether this bar has an outline or not
     * @param colorOutline
     *            the color of the outline
     */
    public static void drawCustomBar(int x, int y, int width, int height, double value, int colorGroundLight, int colorGroundDark, int colorBarLight, int colorBarDark, boolean outlined, int colorOutline) {
        if (value < 0.0D) {
            value = 0.0D;
        }else if (value > 100D) {
            value = 100D;
        }

        int offset = 0;
        if (outlined)
            offset = 1;

        int filledWidth = width;
        filledWidth = width - (offset * 2);
        if (filledWidth < 0)
            filledWidth = 0;
        int filledHeight = width;
        filledHeight = height - (offset * 2);
        if (filledHeight < 0)
            filledHeight = 0;

        int percentFilled = (int) Math.round(value / 100.0D * filledWidth);

        if (outlined)
            drawOutline(x, y, width, height, colorOutline);
        int halfedFilledHeight = filledHeight / 2;

        drawRect(x + offset, y + offset, percentFilled, halfedFilledHeight, colorBarLight);
        drawRect(x + offset, y + offset + halfedFilledHeight, percentFilled, filledHeight - halfedFilledHeight, colorBarDark);

        if (filledWidth - percentFilled > 0) {
            drawRect(x + offset + percentFilled, y + offset, filledWidth - percentFilled, halfedFilledHeight, colorGroundLight);
            drawRect(x + offset + percentFilled, y + offset + halfedFilledHeight, filledWidth - percentFilled, filledHeight - halfedFilledHeight, colorGroundDark);
        }
    }

    /**
     * Draws a tetragon on the screen
     * 
     * @param posX1
     *            x position of the upper left corner
     * @param posX2
     *            x position of the lower left corner
     * @param posY1
     *            y position of the upper left corner
     * @param posY2
     *            y position of the lower left corner
     * @param width1
     *            width of the top edge
     * @param width2
     *            width of the bottom edge
     * @param height1
     *            height of the left edge
     * @param height2
     *            height of the right edge
     * @param color
     *            color of the tetragon (hexa format 0xAARRGGBB)
     */
    public void drawTetragon(int posX1, int posX2, int posY1, int posY2, int width1, int width2, int height1, int height2, int color) {
        if (color == -1)
            return;
        if(width1 < 0) width1 = 0;
        if(width2 < 0) width2 = 0;
        //float f3;
        //if (color <= 0xFFFFFF && color >= 0)
        //    f3 = 1.0F;
        //else
        //    f3 = (color >> 24 & 255) / 255.0F;
        //float f = (color >> 16 & 255) / 255.0F;
        //float f1 = (color >> 8 & 255) / 255.0F;
        //float f2 = (color & 255) / 255.0F;
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        //RenderSystem.color4f(f, f1, f2, f3);
        RenderSystem.disableDepthTest();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(DrawMode.QUADS, VertexFormats.POSITION);
        vertexbuffer.vertex(posX1, (double) posY1 + height1, 0.0D).next();
        vertexbuffer.vertex((double) posX2 + width2, (double) posY2 + height2, 0.0D).next();
        vertexbuffer.vertex((double) posX1 + width1, posY2, 0.0D).next();
        vertexbuffer.vertex(posX2, posY1, 0.0D).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
       //RenderSystem.color3f(1f, 1f, 1f);
    }

    public static int offsetColorPercent(int color, int offsetPercent) {
        int colorOffset;

        int colorPart = (color >> 16 & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset = colorPart << 16;
        colorPart = (color >> 8 & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset += colorPart << 8;
        colorPart = (color & 255);
        colorPart -= colorPart / (100 / offsetPercent);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;
        colorOffset += colorPart;
        return colorOffset;
    }

    public static int offsetColor(int color, int offset) {
        int colorOffset;

        int colorPart = (color >> 16 & 255);
        colorPart += (offset >> 16 & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset = colorPart << 16;
        colorPart = (color >> 8 & 255);
        colorPart += (offset >> 8 & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;

        colorOffset += colorPart << 8;
        colorPart = (color & 255);
        colorPart += (offset & 255);
        if (colorPart > 0xFF)
            colorPart = 0xFF;
        else if (colorPart < 0)
            colorPart = 0;
        colorOffset += colorPart;
        return colorOffset;
    }

    /**
     * Binds a texture to the TextureManager
     * 
     * @param res
     *            The ResourceLocation of the texture that should be bind
     */
    protected void bind(Identifier res) {
        this.mc.getTextureManager().bindTexture(res);
    }

    /**
     * Returns the ResourceLocation for the skin of the player
     * 
     * @param player
     *            the player whose skin should be returned
     * @return the ResourceLocation
     */
    protected static Identifier getPlayerSkin(ClientPlayerEntity player) {
        return player.getSkinTexture();
    }
    
       /**
     * Renders an item on the screen
     * 
     * @param xPos
     *            the x position on the screen
     * @param yPos
     *            the y position on the screen
     * @param partialTicks
     *            the partial ticks (used for animation)
     * @param player
     *            the player who should get the item rendered
     * @param item
     *            the item (via ItemStack)
     */
    protected void renderHotbarItem(int x, int y, float partialTicks, PlayerEntity player, ItemStack item) {
        if (!item.isEmpty()) {
            float f = (float)item.getCooldown() - partialTicks;

            if (f > 0.0F) {
                //RenderSystem.pushMatrix();
                //float f1 = 1.0F + f / 5.0F;
                //RenderSystem.translatef(x + 8, y + 12, 0.0F);
                //RenderSystem.scalef(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                //RenderSystem.translatef((-(x + 8)), (-(y + 12)), 0.0F);
            }

            this.mc.getItemRenderer().renderInGuiWithOverrides(player, item, x, y, 0);

            if (f > 0.0F) {
                //RenderSystem.popMatrix();
            }

            this.mc.getItemRenderer().renderGuiItemOverlay(this.mc.textRenderer, item, x, y);
        }
    }
    
    protected void drawStringWithBackground(MatrixStack ms, String text, int posX, int posY, int colorMain, int colorBackground) {
        this.mc.textRenderer.draw(ms,text, posX + 1, posY, colorBackground);
        this.mc.textRenderer.draw(ms,text, posX - 1, posY, colorBackground);
        this.mc.textRenderer.draw(ms,text, posX, posY + 1, colorBackground);
        this.mc.textRenderer.draw(ms,text, posX, posY - 1, colorBackground);
        this.mc.textRenderer.draw(ms,text, posX, posY, colorMain);
        RenderSystem.enableBlend();
    }
    
    public boolean isChatOpen() {
        return this.mc.currentScreen instanceof net.minecraft.client.gui.screen.ChatScreen;
    }
}
