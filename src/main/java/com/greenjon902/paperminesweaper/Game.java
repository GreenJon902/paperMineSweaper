package com.greenjon902.paperminesweaper;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	private final boolean[][] is_bomb;  // Bomb is true
	private final boolean[][] uncovered;
	private boolean generated = false;

	public Game(int width, int height) {
		this.is_bomb = new boolean[width][height];
		this.uncovered = new boolean[width][height];
	}

	public Game(boolean [][] is_bomb, boolean[][] uncovered) {
		this.is_bomb = is_bomb;
		this.uncovered = uncovered;
		generated = true;
	}


	public char[][] render() {
		char[][] rendered = new char[width()][height()];
		for (int x=0; x<width(); x++) {
			for (int y=0; y<height(); y++) {
				if (uncovered[x][y]) {
					if (is_bomb[x][y]) {
						rendered[x][y] = 'b';
					} else {
						int num = get_surrounding_bomb_count(x, y);
						if (num == 0) {
							rendered[x][y] = ' ';
						} else {
							rendered[x][y] = Character.forDigit(num, 10);
						}
					}
				} else {
					rendered[x][y] = '#';
				}
			}
		}
		return rendered;
	}

	private int get_surrounding_bomb_count(int x, int y) {
		int count = 0;
		for (int[] xy : get_adj(x, y)) {
			if (is_bomb[xy[0]][xy[1]]) {
				count += 1;
			}
		}
		return count;
	}

	public int width() {
		return is_bomb.length;
	}

	public int height() {
		return is_bomb[0].length;
	}

	private void generate(int invalid_x, int invalid_y) {
		int bombs = 0;
		while (bombs != 10) {
			int x = new Random().nextInt(width());
			int y = new Random().nextInt(height());

			if (!(x == invalid_x && y == invalid_y && !is_bomb[x][y])) {
				is_bomb[x][y] = true;
				bombs += 1;
			}
		}
		generated = true;
	}

	public boolean reveal(int x, int y) {
		if (!generated) {
			generate(x, y);
		}

		uncovered[x][y] = true;
		if (get_surrounding_bomb_count(x, y) == 0) {
			for (int[] xy : get_adj(x, y)) {
				if (!uncovered[xy[0]][xy[1]] && !is_bomb[xy[0]][xy[1]]) {
					reveal(xy[0], xy[1]);
				}
			}
		}

		return is_bomb[x][y];
	}

	private int[][] get_adj(int x, int y) {
		ArrayList<int[]> list = new ArrayList<>();
		for (int x2=-1; x2<2; x2++) {
			for (int y2=-1; y2<2; y2++) {
				if (!(x2 == 0 && y2 == 0)) {
					int x3 = x + x2;
					int y3 = y + y2;
					if ((0 <= x3 && x3 < width()) && (0 <= y3 && y3 < height())) {
						list.add(new int[]{x3, y3});
					}
				}
			}
		}
		return list.toArray(int[][]::new);
	}

	public void uncover_all() {
		for (int x = 0; x < width(); x++) {
			for (int y = 0; y < width(); y++) {
				uncovered[x][y] = true;
			}
		}
	}
}