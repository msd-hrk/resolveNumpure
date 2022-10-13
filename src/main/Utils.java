package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String[][][] initCandidateBoard() {
		String[][][] candidateBoard = new String[9][3][3];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					candidateBoard[i][j][k] = "";
				}
			}
		}
		return candidateBoard;
	}
	
	public static boolean chkCompleteCalc(int[][][] board, String[][][] candidateBoard) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					if(board[i][j][k] == 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
	public static boolean chkContradiction(int[][][] board, String[][][] candidateBoard) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					if(board[i][j][k] == 0 && candidateBoard[i][j][k].equals("")) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static List<Integer> crtSeqList() {
		List<Integer> seqList = new ArrayList<>();
		seqList.add(1);
		seqList.add(2);
		seqList.add(3);
		seqList.add(4);
		seqList.add(5);
		seqList.add(6);
		seqList.add(7);
		seqList.add(8);
		seqList.add(9);
		return seqList;
	}
	public static boolean resultChk(int[][][] target) {
		// ブロックチェック
		for(int i = 0; i < 9; i++) {
			List<Integer> seqList = crtSeqList();
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					int idx = seqList.indexOf(target[i][j][k]);
					if(idx == -1) {
						return false;
					}
					seqList.remove(idx);
				}
			}
			if(seqList.size() != 0) {
				return false;
			}
		}
		// 縦のチェック
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				List<Integer> seqList = crtSeqList();
				for(int k = i; k < 9; k = k + 3) {
					for(int l = 0; l < 3; l++) {
						int idx = seqList.indexOf(target[k][l][j]);
						if(idx == -1) {
							return false;
						}
						seqList.remove(idx);
					}
				}
				if(seqList.size() != 0) {
					return false;
				}
			}
		}
		// 横のチェック
		for(int i = 0; i < 9; i = i + 3) {
			for(int j = 0; j < 3; j++) {
				List<Integer> seqList = crtSeqList();
				for(int k = getTargetBlockNum(i); k < getTargetBlockNum(i) + 3; k++) {
					for(int l = 0; l < 3; l++) {
						int idx = seqList.indexOf(target[k][j][l]);
						if(idx == -1) {
							return false;
						}
						seqList.remove(seqList.indexOf(target[k][j][l]));
					}
				}
				if(seqList.size() != 0) {
					return false;
				}
			}
		}
		return true;
	}
	public static void printBoard(int[][][] board) {
		System.out.println("-----------------------------------------------------------------");
		printOneLn(0, board);
		printOneLn(1, board);
		printOneLn(2, board);
		System.out.println("####################");
		printOneLn(3, board);
		printOneLn(4, board);
		printOneLn(5, board);
		System.out.println("####################");
		printOneLn(6, board);
		printOneLn(7, board);
		printOneLn(8, board);
	}

	private static void printOneLn(int line, int[][][] board) {
		int block = getTargetBlockNum(line);
		
		for(int i = block; i < block + 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(board[i][line % 3][j] + " ");
			}
			if(i != block + 2) {
				System.out.print("#");
			}
		}
		System.out.println();
	}

	public static void fileWriteOneLine(Path path, int line, int[][][] board) {
		try {
			FileWriter fw = new FileWriter(path.toString(), true);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			int block = getTargetBlockNum(line);
			
			for(int i = block; i < block + 3; i++) {
				for(int j = 0; j < 3; j++) {
					pw.print(board[i][line % 3][j] + " ");
					if((i == block + 2) && j == 2) {
						pw.println();
					}
				}
				if(i != block + 2) {
					pw.print("#");
				}
			}
			if(line == 2 || line == 5) {
				pw.println("####################");
			} else if (line == 8) {
				pw.println();
				pw.println();
				pw.println();
			}
			pw.close();
		} catch (Exception e) {
			
		}
	}
	public static void fileWrite(Path path, int[][][] board) {
		System.out.println("ファイル出力（" + path.toString() +"）");
		fileWriteOneLine(path, 0, board);
		fileWriteOneLine(path, 1, board);
		fileWriteOneLine(path, 2, board);
		fileWriteOneLine(path, 3, board);
		fileWriteOneLine(path, 4, board);
		fileWriteOneLine(path, 5, board);
		fileWriteOneLine(path, 6, board);
		fileWriteOneLine(path, 7, board);
		fileWriteOneLine(path, 8, board);
	}
	
	public static int getTargetBlockNum(int line) {
		int block = 6;
		if(line == 0 || line == 1 || line == 2) {
			block = 0;
		} else if(line == 3 || line == 4 || line == 5) {
			block = 3;
		}
		return block;
	}

}
