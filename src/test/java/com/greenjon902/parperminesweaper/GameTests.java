package com.greenjon902.parperminesweaper;


import com.greenjon902.paperminesweaper.Game;
import org.junit.Test;

public class GameTests {
	@Test
	public void testRender() {
		Game game = Game.newRandom(10, 10);
		game.uncover_all();

		char[][] rendered = game.render();
		for (int x=0; x<game.width(); x++) {
			for (int y=0; y<game.height(); y++) {
				System.out.print(rendered[x][y]);
			}
			System.out.println();
		}
	}
}
