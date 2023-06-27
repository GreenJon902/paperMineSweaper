package com.greenjon902.paperminesweaper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
	private final boolean[][] is_bomb;  // Bomb is true
	private final boolean[][] uncovered;
	private final boolean[][] flagged;
	private boolean generated = false;

	public Game(int width, int height) {
		this.is_bomb = new boolean[width][height];
		this.uncovered = new boolean[width][height];
		this.flagged = new boolean[width][height];
	}

	public Game(boolean [][] is_bomb, boolean[][] uncovered) {
		this(is_bomb, uncovered, new boolean[is_bomb.length][is_bomb[0].length]);
	}

	public Game(boolean [][] is_bomb, boolean[][] uncovered, boolean[][] flagged) {
		this.is_bomb = is_bomb;
		this.uncovered = uncovered;
		this.flagged = flagged;
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
					if (flagged[x][y]) {
						rendered[x][y] = 'f';
					} else {
						rendered[x][y] = '#';
					}
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
		ArrayList<int[]> invalids = get_adj(invalid_x, invalid_y);
		invalids.add(new int[] {invalid_x, invalid_y});

		int bombs = 0;
		int i = 0;  // So it doesnt loop forever
		while (bombs < 10 && i < 1000) {
			i += 1;

			int x = new Random().nextInt(width());
			int y = new Random().nextInt(height());

			if (!(Utils.intArrayListContainsArray(invalids, new int[] {x, y}) || is_bomb[x][y])) {
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

	private ArrayList<int[]> get_adj(int x, int y) {
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
		return list;
	}

	public void uncover_all() {
		for (int x = 0; x < width(); x++) {
			for (int y = 0; y < height(); y++) {
				uncovered[x][y] = true;
			}
		}
	}

	public void toggle_flag(int x, int y) {
		flagged[x][y] = !flagged[x][y];
	}
}