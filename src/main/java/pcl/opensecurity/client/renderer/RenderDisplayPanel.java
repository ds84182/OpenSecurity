/**
 * 
 */
package pcl.opensecurity.client.renderer;

import org.lwjgl.opengl.GL11;

import pcl.opensecurity.tileentity.TileEntityDisplayPanel;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Caitlyn
 *
 */
public class RenderDisplayPanel extends TileEntitySpecialRenderer {
	private static final float X_OFFSET = 0.5F;
	private static final float Y_OFFSET = 1F - 0.125F;
	private static final float Z_OFFSET = 0.01F;

	private int ticks = 0;
	private String output = "";
	private int count = 0;
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		TileEntityDisplayPanel panel = (TileEntityDisplayPanel) tileEntity;

		float light = tileEntity.getWorldObj().getLightBrightnessForSkyBlocks(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, 15);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light, light);

		RenderManager renderMan = RenderManager.instance;
		
		GL11.glPushMatrix();

		int dir = tileEntity.getBlockMetadata();

		//OpenSecurity.logger.info(dir);
		GL11.glNormal3f(0, 1, 0);
		if (dir == 2) {
			GL11.glTranslatef((float)x + X_OFFSET, (float)y + Y_OFFSET, (float)z - Z_OFFSET);
			GL11.glRotatef(0F, 0F, 1F, 0F);
		} else if (dir == 5) {
			GL11.glTranslatef((float)x + 1f + Z_OFFSET, (float)y + Y_OFFSET, (float)z + X_OFFSET);
			GL11.glRotatef(270F, 0F, 1F, 0F);
		} else if (dir == 3) {
			GL11.glTranslatef((float)x + X_OFFSET, (float)y + Y_OFFSET, (float)z + 1F + Z_OFFSET);
			GL11.glRotatef(180F, 0F, 1F, 0F);
		} else if (dir == 4) {
			GL11.glTranslatef((float)x - Z_OFFSET, (float)y + Y_OFFSET, (float)z + X_OFFSET);
			GL11.glRotatef(90, 0F, 1F, 0F);
		}

		GL11.glScalef(-0.016666668F, -0.016666668F, 0.016666668F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		++this.ticks;
		if (this.ticks > 20) {
			if (panel.getScreenText().length() > 6) {
				output = scrollText(panel.getScreenText());
			} else {
				output = panel.getScreenText();
			}
		}
		renderMan.getFontRenderer().drawString(output, -37 / 2, 0, panel.getScreenColor());
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	private String scrollText(String text) {
		FontRenderer fontRenderer = RenderManager.instance.getFontRenderer();
		text = "       " + text + "        ";
		if (text.length() > count + 6) {
			output = text.substring(count, count + 6);
			if (fontRenderer.getStringWidth(output) / 6 < 5) {
				output = text.substring(count, count + 7);
			}
			count++;
			this.ticks = 0;
			if (count > text.length()) {
				count = 0;
			}
		} else {
			count = 0;
		}
		return output;
	}
	
}
