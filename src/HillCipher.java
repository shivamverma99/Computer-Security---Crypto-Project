
import Jama.Matrix;
public class HillCipher {
	
	static void getKeyMatrix(String key, int matrixKey[][]) {
		int x = 0;
		for(int y = 0; y < matrixKey.length; y++) {
			for(int z = 0; z < matrixKey[y].length; z++) {
				matrixKey[y][z] = (key.charAt(x))%65;
				x++;
			}
		}
	}
	static String encrypt(String key, String plainText) {
		int[][] matrixKey = new int[plainText.length()][plainText.length()];
		int x = 0;
		for(int y = 0; y < matrixKey.length; y++) {
			for(int z = 0; z < matrixKey[y].length; z++) {
				matrixKey[y][z] = (key.charAt(x))%65;
				x++;
			}
		}
		int[][] plainVector = new int[plainText.length()][1];
		int[][] cipherVector = new int[plainText.length()][1];
		for(x = 0; x < plainVector.length; x++) {
			plainVector[x][0] = (plainText.charAt(x))%65;
		}
		for(x = 0; x < cipherVector.length; x++) {
			for(int y = 0; y < 1; y++) {
				cipherVector[x][y] = 0;
				for(int z = 0; z< cipherVector.length; z++) {
					cipherVector[x][y] += matrixKey[x][z] * plainVector[z][y];
				}
				cipherVector[x][y] = cipherVector[x][y] % 26;
			}
		}
		String cipherText = "";
		for(x = 0; x < cipherVector.length; x++) {
			cipherText += (char)(cipherVector[x][0] + 65);
		}
		return cipherText;
	}
	static String decrypt(String key, String cipherText) {
		//Matrix test = new Matrix(key.length(), key.length());
		int x = 0;
		int[][] matrixKey = new int[cipherText.length()][cipherText.length()];
		for(int y = 0; y < matrixKey.length; y++) {
			for(int z = 0; z < matrixKey[y].length; z++) {
				matrixKey[y][z] = (key.charAt(x))%65;
				x++;
			}
		}
		int n = cipherText.length();
		Matrix inverse = new Matrix(n, n);
		for(x = 0; x < cipherText.length(); x++) {
			for(int y = 0; y < cipherText.length(); y++) {
				inverse.set(x, y, (double)matrixKey[x][y]);
			}
		}
		inverse = inverse.inverse();
		int[][] inverseKey = new int[cipherText.length()][cipherText.length()];
		for(x = 0; x < cipherText.length(); x++) {
			for(int y = 0; y < cipherText.length(); y++) {
				inverseKey[x][y] = (int)inverse.get(x, y);
			}
		}
		int[][] plainVector = new int[cipherText.length()][1];
		int[][] cipherVector = new int[cipherText.length()][1];
		for(x = 0; x < cipherVector.length; x++) {
			cipherVector[x][0] = (cipherText.charAt(x))%65;
		}
		for(x = 0; x < plainVector.length; x++) {
			for(int y = 0; y < 1; y++) {
				plainVector[x][y] = 0;
				for(int z = 0; z< plainVector.length; z++) {
					plainVector[x][y] += inverseKey[x][z] * cipherVector[z][y];
				}
				plainVector[x][y] = plainVector[x][y] % 26;
			}
		}
		String plainText = "";
		for(x = 0; x < plainVector.length; x++) {
			plainText += (char)(plainVector[x][0] + 65);
		}
		return plainText;
	}
}
