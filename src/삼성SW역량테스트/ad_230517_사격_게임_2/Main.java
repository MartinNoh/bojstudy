package 삼성SW역량테스트.ad_230517_사격_게임_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	static int T, H, W, maxScore;
	static int[][] D;
	static boolean[][] visit;
	static List<Node> startList;
	static boolean[] startListVisit;
	static List<List<Node>> listCombination;
	
	static void shootRightUp(int i, int j) {
		visit[i][j] = true;
		if (i - 1 >= 0 && j < W) {
			shootRightUp(i - 1, j + 1);
		}
	}
	
	static void shootRightStraight(int i, int j) {
		visit[i][j] = true;
		if (j + 1 < W) {
			shootRightStraight(i, j + 1);
		}
	}
	
	static void shootRightDown(int i, int j) { // == shootDownRight.
		visit[i][j] = true;
		if (i + 1 < H && j + 1 < W) {
			shootRightDown(i + 1, j + 1);
		}
	}
	
	static void shootDownLeft(int i, int j) {
		visit[i][j] = true;
		if (j - 1 >= 0 && i + 1 < H) {
			shootDownLeft(i + 1, j - 1);
		}
	}
	
	static void shootDownStraight(int i, int j) {
		visit[i][j] = true;
		if (i + 1 < H) {
			shootDownStraight(i + 1, j);
		}
	}
	
	static void combinateList(int depth, int r) {
		if (r == 0) {
			List<Node> tempList = new ArrayList<>();
			for (int i = 0; i < startListVisit.length; i++) {
	            if (startListVisit[i]) {
	            	tempList.add(startList.get(i));
	            }
	        }
			listCombination.add(tempList);
			return;
		}
		
		for (int i = depth; i < startListVisit.length; i++) {
			startListVisit[i] = true;
			combinateList(i + 1, r - 1);
			startListVisit[i] = false;
		}
	}
	
	static int getTotalScore() {
		int total = 0;
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (visit[i][j]) {
					total += D[i][j];
				}
			}
		}
		return total;
	}
	
	static class Node {
		private int i;
		private int j;
		
		public Node(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
	
	public static void main(String args[]) throws IOException {
		// Input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine()); // T: The number of Test case.
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());  // H: Height.
			W = Integer.parseInt(st.nextToken());  // W: Width.
			
			D = new int[H][W];
			visit = new boolean[H][W];
			maxScore = Integer.MIN_VALUE;
			
			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < W; j++) {
					D[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			startList = new ArrayList<>();
			for (int i = 0; i < H; i++) {
				startList.add(new Node(i, 0));
			}
			for (int j = 1; j < W; j++) {
				startList.add(new Node(0, j));
			}

			startListVisit = new boolean[startList.size()];
			listCombination = new ArrayList<>();
			combinateList(0, 3);
			
			// Process
			for (List<Node> list : listCombination) {
				for (Node node : list) {
					visit = new boolean[H][W];
					
					for (int k = 0; k < 3; k++) {
						switch (k) {
						case 0:
							if (node.i == 0 && node.j == 0) {
								shootRightStraight(node.i, node.j);
							} else if (node.i == 0) {
								shootDownLeft(node.i, node.j);
							} else if (node.j == 0) {
								shootRightUp(node.i, node.j);
							}
							break;
						case 1:
							if (node.i == 0 && node.j == 0) {
								shootDownStraight(node.i, node.j);
							} else if (node.i == 0) {
								shootDownStraight(node.i, node.j);
							} else if (node.j == 0) {
								shootRightStraight(node.i, node.j);
							}
							break;
						case 2:
							if (node.i == 0 && node.j == 0) {
								shootRightDown(node.i, node.j);
							} else if (node.i == 0) {
								shootRightDown(node.i, node.j);
							} else if (node.j == 0) {
								shootRightDown(node.i, node.j);
							}
							break;
						default:
							break;
						}
					}
					maxScore = Math.max(maxScore, getTotalScore());
				}
			}
			
			// Output
			StringBuilder sb = new StringBuilder();
			sb.append("#");
			sb.append(String.valueOf(t));
			sb.append(" ");
			sb.append(String.valueOf(maxScore));
			
			bw.write(sb.toString());
			bw.newLine();
			
//			bw.newLine();
//			bw.write("[Input check: " + String.valueOf(t) + "]");
//			bw.newLine();
//			for (int i = 0; i < H; i++) {
//				for (int j = 0; j < W; j++) {
//					bw.write(String.valueOf(D[i][j])+ " ");
//				}
//				bw.newLine();
//			}
		}

		bw.flush();
		bw.close();
		br.close();
	}
}
