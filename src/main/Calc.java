package main;

import java.util.ArrayList;
import java.util.List;

public class Calc {
	
	private int[][][] board;
	
	private List<Integer> candidateList;
	
	private String[][][] candidateBoard;
	
	public Calc(int[][][] board, String[][][] candidateBoard) {
		this.board = board;
		this.candidateBoard = candidateBoard;
	}
	
	public int[][][] calculation() {
		while (true) {
			init();
			int updCnt = 0;
			// 候補ボード作成
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 3; j++) {
					for(int k = 0; k < 3; k++) {
						if(board[i][j][k] != 0) {
							continue;
						}
						candidateList = crtSeqList();
						
						blockCalc(i, j, k);
						verticalCalc(i, j, k);
						besideCalc(i, j, k);
						
						candidateBoard[i][j][k]= cnvStr(candidateList);
					}
				}
			}
			// 値絞り込み
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 3; j++) {
					for(int k = 0; k < 3; k++) {
						int result = 0;
						if(candidateBoard[i][j][k] == "") {
							continue;
						}
						if(candidateBoard[i][j][k].length() == 1) {
							board[i][j][k] = Integer.parseInt(candidateBoard[i][j][k].substring(0, 1));
							updCnt++;
							continue;
						}
						
						result = decideValue(i, j, k);
						
						if(result != 0) {
							board[i][j][k] = result;
							updCnt++;
							continue;
						}
					}
				}
			}
			
			if(updCnt == 0) {
				if(Utils.chkCompleteCalc(board, candidateBoard)) {
					break;
				}else {
					if(Utils.chkContradiction(board, candidateBoard)) {
						AssignmentCalc assignCalc = new AssignmentCalc(board, candidateBoard);
						return assignCalc.assignmentCalc();
					} else {
						return board;
					}
				}
				
			}
//			printBoard();
		}
		return this.board;
	}
	
	private int decideValue(int b, int h, int w) {
		String target = candidateBoard[b][h][w];
		if(target.length() == 0) {
			return 0;
		}
		for(int len = 0; len < target.length(); len++) {
			String tmp = target.substring(len, len + 1);
			boolean isDuplicate = false;
			
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					
					if (h == j && w == k) {
						continue;
					}
					if(candidateBoard[b][j][k].contains(tmp)) {
						isDuplicate = true;
					}
				}
			}
			if(!isDuplicate) {
				// 重複していない場合
				return Integer.parseInt(tmp);
			}

			boolean isDuplicateV = false;
			int verticalValue = getVerticalIdx(b);
			for(int j = verticalValue; j < 9; j = j + 3) {
				for(int k = 0; k < 3; k++) {
					
					if (b == j && h == k) {
						continue;
					}
					if(candidateBoard[j][k][w].contains(tmp)) {
						isDuplicateV = true;
					}
				}
			}
			if(!isDuplicateV) {
				// 重複していない場合
				return Integer.parseInt(tmp);
			}

			boolean isDuplicateB = false;
			int besideValue = getBesideIdx(b);
			for(int j = besideValue; j < besideValue + 3; j++) {
				for(int k = 0; k < 3; k++) {
					
					if (b == j && w == k) {
						continue;
					}
					if(candidateBoard[j][h][k].contains(tmp)) {
						isDuplicateB = true;
					}
				}
			}
			if(!isDuplicateB) {
				// 重複していない場合
				return Integer.parseInt(tmp);
			} else {
				continue;
			}
		}
		return 0;
	}
	
	private void blockCalc(int b, int h, int w) {
		for(int j = 0; j < 3; j++) {
			for(int k = 0; k < 3; k++) {
				int value = board[b][j][k];
				if (h == j && w == k) {
					continue;
				}
				if(value != 0 && candidateList.indexOf(value) != -1) {
					candidateList.remove(candidateList.indexOf(value));
				}
			}
		}
	}
	
	private void verticalCalc(int b, int h, int w) {
		int verticalValue = getVerticalIdx(b);
		for(int j = verticalValue; j < 9; j = j + 3) {
			for(int k = 0; k < 3; k++) {
				int value = board[j][k][w];
				if (b == j && h == k) {
					continue;
				}
				if(value != 0 && candidateList.indexOf(value) != -1) {
					candidateList.remove(candidateList.indexOf(value));
				}
			}
		}
		
	}
	
	private void besideCalc(int b, int h, int w) {
		int besideValue = getBesideIdx(b);
		for(int j = besideValue; j < besideValue + 3; j++) {
			for(int k = 0; k < 3; k++) {
				int value = board[j][h][k];
				if (b == j && w == k) {
					continue;
				}
				if(value != 0 && candidateList.indexOf(value) != -1) {
					candidateList.remove(candidateList.indexOf(value));
				}
			}
		}
		
	}
	
	private List<Integer> crtSeqList() {
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
	
	
	private String cnvStr(List<Integer> list) {
		String value = "";
		for(Integer v : list) {
			value = value + v;
		}
		return value;
	}
	
	private int getBesideIdx(int i) {
		if(i < 3) {
			return 0;
		} else if(i < 6) {
			return 3;
		} else {
			return 6;
		}
	}
	private int getVerticalIdx(int i) {
		if(i == 0 || i == 3 || i == 6) {
			return 0;
		} else if(i == 1 || i == 4 || i == 7) {
			return 1;
		} else {
			return 2;
		}
	}
	
	private void init() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					candidateBoard[i][j][k] = "";
				}
			}
		}
	}
}
