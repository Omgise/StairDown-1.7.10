package invalid.myask.stairdown.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.realms.RealmsMth;
import net.minecraft.util.IIcon;

import invalid.myask.stairdown.api.IParentedBlock;

public class BlockMadeSlab extends BlockSlab implements IParentedBlock {

    protected final Block parentBlock;
    protected int parentMetaMin;
    protected int parentMetaMax;
    protected boolean isDouble;

    public BlockMadeSlab(boolean isDouble, Block parent, int metaMin, int metaMax) {
        super(isDouble, parent.getMaterial());
        if (parentMetaMin > parentMetaMax) throw new IndexOutOfBoundsException(
            "Meta min > meta max for attempted slabbing of " + parent.getUnlocalizedName());
        parentMetaMin = metaMin;
        parentMetaMax = metaMax;
        parentBlock = parent;
        this.isDouble = isDouble;
        useNeighborBrightness = true;
    }

    @Override
    public String func_150002_b(int p_150002_1_) {
        return parentBlock.getUnlocalizedName() + (isDouble ? ".doubleSlab." : ".slab.")
            + parentMetaMin
            + (parentMetaMax > parentMetaMin ? ".." + parentMetaMax : "");
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (getBlockFromItem(itemIn) instanceof BlockMadeSlab bs
            && !itemIn.getUnlocalizedName().contains("doubleSlab")) {
            for (int meta = parentMetaMin; meta <= parentMetaMax; meta++) list.add(new ItemStack(itemIn, 1, meta));
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return parentBlock.getIcon(side, RealmsMth.clamp(meta & -9, parentMetaMin, parentMetaMax));
    }

    @Override
    public Block getParentBlock() {
        return parentBlock;
    }

    @Override
    public int getParentMeta() {
        return parentMetaMin;
    }

    public int getMetaMax() {
        return parentMetaMax;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {}
}
