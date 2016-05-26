package org.acouster.com;

import org.acouster.context.ContextBitmapFragment;
import org.acouster.graphics.anim.BubblePopVisual;
import org.acouster.logic.Sprite2D;

public class BubblePopVisual_acouster1 extends BubblePopVisual
{
	public BubblePopVisual_acouster1(Sprite2D sprite) {
		super(sprite,
				Constants.Filenames.acouster,
				Constants.Filenames.acouster_cutoff,
				Constants.Filenames.acouster_globs,
				Constants.Filenames.bubble_pop, 5);
	}

	/** Override this function with code from the bubble XML generator tool */

	@Override
	public void initBubbleStructs()
	{
		ContextBitmapFragment local_glob_g1 = new ContextBitmapFragment(bmpGlobs, 10, 10, 105, 105);
		ContextBitmapFragment local_glob_g3 = new ContextBitmapFragment(bmpGlobs, 233, 10, 38, 38);
		ContextBitmapFragment local_glob_g2 = new ContextBitmapFragment(bmpGlobs, 146, 10, 56, 56);
		ContextBitmapFragment local_glob_g5 = new ContextBitmapFragment(bmpGlobs, 406, 10, 66, 66);
		ContextBitmapFragment local_glob_g4 = new ContextBitmapFragment(bmpGlobs, 302, 10, 73, 73);
		ContextBitmapFragment local_glob_g6 = new ContextBitmapFragment(bmpGlobs, 503, 10, 45, 45);
		ContextBitmapFragment local_shadow_g5 = new ContextBitmapFragment(bmpGlobs, 405, 133, 82, 82);
		ContextBitmapFragment local_shadow_g4 = new ContextBitmapFragment(bmpGlobs, 301, 133, 89, 89);
		ContextBitmapFragment local_shadow_g6 = new ContextBitmapFragment(bmpGlobs, 502, 133, 61, 61);
		ContextBitmapFragment local_shadow_g1 = new ContextBitmapFragment(bmpGlobs, 9, 133, 121, 121);
		ContextBitmapFragment local_shadow_g3 = new ContextBitmapFragment(bmpGlobs, 232, 133, 54, 54);
		ContextBitmapFragment local_shadow_g2 = new ContextBitmapFragment(bmpGlobs, 145, 133, 72, 72);
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  333, 180, 33, 33), local_glob_g3, local_shadow_g3)); // 0
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  299, 180, 33, 33), local_glob_g3, local_shadow_g3)); // 1
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  299, 111, 67, 67), local_glob_g4, local_shadow_g4)); // 2
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  197, 111, 99, 99), local_glob_g1, local_shadow_g1)); // 3
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  128, 143, 67, 67), local_glob_g4, local_shadow_g4)); // 4
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  161, 111, 32, 32), local_glob_g3, local_shadow_g3)); // 5
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  128, 111, 32, 32), local_glob_g3, local_shadow_g3)); // 6
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  92, 180, 33, 33), local_glob_g3, local_shadow_g3)); // 7
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  59, 180, 33, 33), local_glob_g3, local_shadow_g3)); // 8
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  59, 111, 67, 67), local_glob_g4, local_shadow_g4)); // 9
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  7, 163, 50, 50), local_glob_g2, local_shadow_g2)); // 10
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  7, 111, 50, 50), local_glob_g2, local_shadow_g2)); // 11
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  310, 69, 38, 38), local_glob_g6, local_shadow_g6)); // 12
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  303, 7, 60, 60), local_glob_g5, local_shadow_g5)); // 13
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  240, 45, 60, 60), local_glob_g5, local_shadow_g5)); // 14
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  257, 7, 38, 38), local_glob_g6, local_shadow_g6)); // 15
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  205, 76, 33, 33), local_glob_g3, local_shadow_g3)); // 16
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  171, 76, 33, 33), local_glob_g3, local_shadow_g3)); // 17
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  171, 7, 67, 67), local_glob_g4, local_shadow_g4)); // 18
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  108, 45, 60, 60), local_glob_g5, local_shadow_g5)); // 19
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  108, 7, 38, 38), local_glob_g6, local_shadow_g6)); // 20
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  7, 7, 99, 99), local_glob_g1, local_shadow_g1)); // 21

	}

}
