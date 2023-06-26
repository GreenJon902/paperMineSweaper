package com.greenjon902.paperminesweaper;

import java.util.Random;

public class Game {
	private final boolean[][] is_bomb;  // Bomb is true
	private final boolean[][] uncovered;

	private Game(boolean[][] is_bomb, boolean[][] uncovered) {
		this.is_bomb = is_bomb;
		this.uncovered = uncovered;
	}

	public static Game newRandom(int width, int height) {
		boolean[][] parts = new boolean[width][height];
		for (int i=0; i<10; i++) {
			parts[new Random().nextInt(width)][new Random().nextInt(height)] = true;
		}
		return new Game(parts, new boolean[width][height]);
	}

	public char[][] render() {
		char[][] rendered = new char[width()][height()];
		for (int x=0; x<width(); x++) {
			for (int y=0; y<width(); y++) {
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
					rendered[x][y] = '█';
				}
			}
		}
		return rendered;
	}

	private int get_surrounding_bomb_count(int x, int y) {
		int count = 0;
		count += safe_is_bomb(x - 1, y - 1) ? 1 : 0;
		count += safe_is_bomb(x - 1, y + 0) ? 1 : 0;
		count += safe_is_bomb(x - 1, y + 1) ? 1 : 0;
		count += safe_is_bomb(x + 0, y - 1) ? 1 : 0;
		count += safe_is_bomb(x + 0, y + 1) ? 1 : 0;
		count += safe_is_bomb(x + 1, y - 1) ? 1 : 0;
		count += safe_is_bomb(x + 1, y + 0) ? 1 : 0;
		count += safe_is_bomb(x + 1, y + 1) ? 1 : 0;
		return count;
	}

	private boolean safe_is_bomb(int x, int y) {
		if ((0 <= x && x < width()) && (0 <= y && y < height())) {
			return is_bomb[x][y];
		}
		return false;
	}

	public int width() {
		return is_bomb.length;
	}

	public int height() {
		return is_bomb[0].length;
	}

	public void uncover_all() {
		for (int x=0; x<width(); x++) {
			for (int y = 0; y < width(); y++) {
				uncovered[x][y] = true;
			}
		}
	}
}