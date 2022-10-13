package main;

public class AssignmentCalc {

	private int[][][] board;
	
	private String[][][] candidateBoard;
	
	private int block = 0;
	private int line = 0;
	private int col = 0;
	private String strCandidateNum = "";

	public AssignmentCalc(int[][][] board, String[][][] candidateBoard) {
		this.board = board;
		this.candidateBoard = candidateBoard;
	}
	
	public int[][][] assignmentCalc(){
		setAssignmentNum();
		
		int[][][] avoidNull = null;
		for(int i = 0; i < strCandidateNum.length(); i++) {
			int[][][] tmpBoard = cloneBoard(board);
			tmpBoard[block][line][col] = Integer.parseInt(strCandidateNum.substring(i, i + 1));
			String[][][] tmpCandidateBoard = cloneCandidateBoard(candidateBoard);
			
			Calc tmpCalc = new Calc(tmpBoard, tmpCandidateBoard);
			tmpBoard = tmpCalc.calculation();
			
			if(Utils.resultChk(tmpBoard)) {
				return tmpBoard;
			}
			avoidNull = tmpBoard;
		}
		
		return avoidNull;
	}
	private void setAssignmentNum() {
		int assignCnt = 2;
		label:while(true) {
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 3; j++) {
					for(int k = 0; k < 3; k++) {
						if(candidateBoard[i][j][k].length() == assignCnt) {
							block = i;
							line = j;
							col = k;
							strCandidateNum = candidateBoard[i][j][k];
							break label;
						}
					}
				}
			}
			assignCnt++;
		}
	}
	private int[][][] cloneBoard(int[][][] board){
		int[][][] cloneB = new int[9][3][3];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					cloneB[i][j][k] = board[i][j][k];
				}
			}
		}
		return cloneB;
	}
	private String[][][] cloneCandidateBoard(String[][][] candidateBoard){
		String[][][] clone = new String[9][3][3];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					clone[i][j][k] = candidateBoard[i][j][k].substring(0);
				}
			}
		}
		return clone;
	}
}
