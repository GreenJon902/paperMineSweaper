package com.greenjon902.parperminesweaper;


import com.greenjon902.paperminesweaper.Game;
import org.junit.Test;

public class GameTests {
	@Test
	public void testRenderEmptyBoard() {
		Game game = new Game(
				new boolean[][]{
						{false, false, false},
						{false, false, false},
						{false, false, false}
				},
				new boolean[][]{
						{false, false, false},
						{false, false, false},
						{false, false, false}
				});
		run(game);
	}

	@Test
	public void testRenderNotEmptyBoard() {
		Game game = new Game(
				new boolean[][]{
						{false, false, false},
						{false, false, false},
						{false, true, false}
				},
				new boolean[][]{
						{false, false, false},
						{false, false, false},
						{false, false, false}
				});
		run(game);
	}

	@Test
	public void testRenderRandomBoard() {
		Game game = new Game(10, 10, 10);
		run(game);
	}


	public void run(Game game){
		char[][] rendered = game.render();
		for (int x=0; x<game.width(); x++) {
			for (int y=0; y<game.height(); y++) {
				System.out.print(rendered[x][y]);
			}
			System.out.println();
		}
		System.out.println();

		game.reveal(0, 0);  // Reveal 0, 0 (and any adjacent none numbers)

		rendered = game.render();
		for (int x=0; x<game.width(); x++) {
			for (int y=0; y<game.height(); y++) {
				System.out.print(rendered[x][y]);
			}
			System.out.println();
		}
		System.out.println();

		game.uncover_all();

		rendered = game.render();
		for (int x=0; x<game.width(); x++) {
			for (int y=0; y<game.height(); y++) {
				System.out.print(rendered[x][y]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
