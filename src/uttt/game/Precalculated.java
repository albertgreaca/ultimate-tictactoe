package uttt.game;

public class Precalculated {
	public static int[] minx = new int[20000];
	public static int[] maxx = new int[20000];
	public static int[] mino = new int[20000];
	public static int[] maxo = new int[20000];
	public static int[] nexminx = new int[20000];
	public static int[] nexmaxx = new int[20000];
	public static int[] nexmino = new int[20000];
	public static int[] nexmaxo = new int[20000];
	public static int[] p3 = { 1, 3, 9, 27, 81, 243, 729, 2187, 6561 };
	public static int ok = 0;

	public static boolean checkfull(int nr) {
		int[] a = new int[10];
		int i;
		for (i = 0; i < 9; i++) {
			a[i] = nr % 3;
			nr = nr / 3;
		}
		for (i = 0; i < 9; i++)
			if (a[i] == 0)
				return false;
		return true;
	}

	public static boolean checkwon(int nr, int sym) {
		int[] a = new int[10];
		int i;
		for (i = 0; i < 9; i++) {
			a[i] = nr % 3;
			nr = nr / 3;
		}
		if (a[0] == sym && a[0] == a[1] && a[1] == a[2])
			return true;
		if (a[3] == sym && a[3] == a[4] && a[4] == a[5])
			return true;
		if (a[6] == sym && a[6] == a[7] && a[7] == a[8])
			return true;
		if (a[0] == sym && a[0] == a[3] && a[3] == a[6])
			return true;
		if (a[1] == sym && a[1] == a[4] && a[4] == a[7])
			return true;
		if (a[2] == sym && a[2] == a[5] && a[5] == a[8])
			return true;
		if (a[0] == sym && a[0] == a[4] && a[4] == a[8])
			return true;
		if (a[2] == sym && a[2] == a[4] && a[4] == a[6])
			return true;
		return false;
	}

	public static int minimizer(int nr, int sym, int[] resmin, int[] resmax, int[] movmin, int[] movmax) {
		int[] a = new int[10];
		int i, cnr = nr;
		for (i = 0; i < 9; i++) {
			a[i] = nr % 3;
			nr = nr / 3;
		}
		nr = cnr;
		if (checkwon(nr, sym)) {
			resmin[nr] = -1;
			movmin[nr] = -1;
			return -1;
		}
		if (checkwon(nr, 3 - sym)) {
			resmin[nr] = 1;
			movmin[nr] = -1;
			return 1;
		}
		if (checkfull(nr)) {
			resmin[nr] = -1;
			movmin[nr] = -1;
			return -1;
		}
		int mini = 10, poz = -1;
		for (i = 0; i < 9; i++)
			if (a[i] == 0) {
				a[i] = sym;
				nr += sym * p3[i];
				int aux = maximizer(nr, 3 - sym, resmin, resmax, movmin, movmax);
				if (aux < mini) {
					mini = aux;
					poz = i;
				}
				nr -= sym * p3[i];
				a[i] = 0;
			}
		if (mini < resmin[nr]) {
			resmin[nr] = mini;
			movmin[nr] = poz;
		}
		return mini;
	}

	public static int maximizer(int nr, int sym, int resmin[], int resmax[], int movmin[], int movmax[]) {
		int[] a = new int[10];
		int i, cnr = nr;
		for (i = 0; i < 9; i++) {
			a[i] = nr % 3;
			nr = nr / 3;
		}
		nr = cnr;
		if (checkwon(nr, sym)) {
			resmax[nr] = 1;
			movmax[nr] = -1;
			return 1;
		}
		if (checkwon(nr, 3 - sym)) {
			resmax[nr] = -1;
			movmax[nr] = -1;
			return -1;
		}
		if (checkfull(nr)) {
			resmax[nr] = -1;
			movmax[nr] = -1;
			return -1;
		}
		int maxi = -10, poz = -1;
		for (i = 0; i < 9; i++)
			if (a[i] == 0) {
				a[i] = sym;
				nr += sym * p3[i];
				int aux = minimizer(nr, 3 - sym, resmin, resmax, movmin, movmax);
				if (aux > maxi) {
					maxi = aux;
					poz = i;
				}
				nr -= sym * p3[i];
				a[i] = 0;
			}
		if (maxi > resmax[nr]) {
			resmax[nr] = maxi;
			movmax[nr] = poz;
		}
		return maxi;
	}

	public static void setandrun() {
		int i;
		ok = 1;
		for (i = 0; i <= 19682; i++) {
			minx[i] = mino[i] = 10;
			maxx[i] = maxo[i] = -10;
		}
		minimizer(0, 1, minx, maxo, nexminx, nexmaxo);
		minimizer(0, 2, mino, maxx, nexmino, nexmaxx);
		maximizer(0, 1, mino, maxx, nexmino, nexmaxx);
		maximizer(0, 2, minx, maxo, nexminx, nexmaxo);
	}
}
