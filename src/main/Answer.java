package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Answer {

	public static void main(String[] args) {

		final String OS_NAME = System.getProperty("os.name").toLowerCase();
		String delimiter = "";
		if(OS_NAME.startsWith("windows")) {
			delimiter = "\\";
		} else if(OS_NAME.startsWith("linux")) {
			delimiter = "/";
		}
		if(args.length == 0) {
			System.out.println("第一引数に問題のディレクトリを指定してください");
			return;
		}
		Path dirPath = Paths.get(args[0]);
//		Path dirPath = Paths.get("C:\\work");
		Path ansDir = null;
		List<Path> fileList = new ArrayList<>();
		try {
			Files.list(dirPath).forEach(p -> fileList.add(p));
			ansDir=Paths.get(dirPath.toString() + delimiter + "answer");
			if(Files.exists(ansDir)) {
				System.out.println("answerディレクトリを削除してください");
				return;
			}
			Files.createDirectory(ansDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("対象ファイル");
		fileList.forEach(fName -> System.out.println(fName.toString()));
		System.out.println();
		for(Path p : fileList) {
			System.out.println(p.toString() + "開始");
			Path ansFile = Paths.get(ansDir.toString() + delimiter + p.getFileName());
			int[][][] board = readQuestion(p);
			System.out.print("開始時の");
			Utils.fileWrite(ansFile, board);
			Calc calc = new Calc(board, Utils.initCandidateBoard());
			board = calc.calculation();
			Utils.printBoard(board);
			System.out.print("完了時の");
			Utils.fileWrite(ansFile, board);
			System.out.println();
		}
	}
	
	private static int[][][] readQuestion(Path file) {
		int[][][] board = new int[9][3][3];
		try(BufferedReader br = Files.newBufferedReader(file)){
			String line;
			int lineCnt = 0;
			while((line = br.readLine()) != null) {
				
				int block = Utils.getTargetBlockNum(lineCnt);
				for(int i  = block; i < block + 3; i++) {
					for(int j = getCutIdx(i); j < getCutIdx(i) + 3; j++) {
						board[i][lineCnt % 3][j % 3] = Integer.parseInt(line.substring(j, j + 1));
					}
				}
				lineCnt++;
			}
		} catch(Exception e) {
			
		}
		return board;
	}
	
	private static int getCutIdx(int i) {
		if(i % 3 == 0) {
			return 0;
		} else if (i % 3 == 1){
			return 3;
		}
		return 6;
	}
}
