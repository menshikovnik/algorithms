import java.util.Iterator;
import java.util.NoSuchElementException;

public class v1<v2> {
    private v2[] v3;
    private int v4;
    private int v5;
    private int v6;

    public v1(int v6) {
        v3 = (v2[]) new Object[v6];
        v4 = 0;
        v5 = -1;
        this.v6 = v3.length - 1;

    }
    public Iterator<v2> v7() {
        return new v8();
    }

    public class v8 implements Iterator<v2> {
        private v2 v9;
        private int i = v4;
        private int v10 = 0;
        public v8() {
            this.v9 = v3[i];
        }

        public boolean hasNext() {
            return v10 <= v6;
        }

        public v2 next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            v2 v11 = v9;
            if (i == v6){
                i = -1;
            }
            v9 = v3[i + 1];
            i++;
            v10++;
            return v11;
        }

        public void v12() {
            throw new UnsupportedOperationException();
        }
    }

    public void v13(v2 v11) {
        if (v14()) {
            v15();
        }
        if (v4 == 0 && v3[v4] != null && v16(v6 - 1)) {
            v4 = v6;
            v3[v4] = v11;
            return;
        }
        if (v4 != 0) v4--;
        if (v16(v4)) {
            v3[v4] = v11;
        }
    }

    public void v17(v2 v11) {
        if (v14()) {
            v15();
        }

        if (v5 == 0 && v16(0)) {
            v3[v5] = v11;
            return;
        }
        v5++;
        if (v5 > v6 && v16(0)) {
            v5 = 0;
        }
        if (v16(v5)) {
            v3[v5] = v11;
            return;
        }
        if (v5 == 0 && !v16(0)) {
            v5++;
            if (v16(v5)) {
                v3[v5] = v11;
            }
        }
    }

    public v2 v18() {
        v2 v11 = null;
        if (!v16(v4)) {
            v11 = v3[v4];
            v3[v4] = null;
            if (v4 != v6) {
                v4++;
            } else v4 = 0;
        }
        return v11;
    }

    public v2 v19() {
        v2 v11 = null;
        if (v5 == -1) {
            v5++;
            v11 = v3[v5];
            v3[v5] = null;
            v5 = v6;
            return v11;
        }
        if (v5 == 0 && !v16(v5)) {
            v11 = v3[v5];
            v3[v5] = null;
            if (!v16(v6)) {
                v5 = v6;
                return v11;
            }
        }
        if (!v16(v5)) {
            v11 = v3[v5];
            v3[v5] = null;
            v5--;
        }
        if (v5 == -1) {
            v5 = v6;
        }
        return v11;
    }

    private boolean v16(int v20) {
        return v20 >= 0 && v3[v20] == null;
    }

    private boolean v14() {
        for (v2 v11 : v3) {
            if (v11 == null) {
                return false;
            }
        }
        return true;
    }

    private int v6() {
        return v3.length;
    }

    public v2 v21() {
        return v3[v4];
    }

    public v2 v22() {
        return v3[v5];
    }

    private void v15() {
        v6 = v6 * 2;
        v2[] v23 = (v2[]) new Object[v6];
        System.arraycopy(v3, 0, v23, 0, v3.length);
        int v24 = v3.length - v4;
        int v25 = v4;
        v3 = (v2[]) new Object[v6];
        v6 -= 1;
        if (v4 == 0) {
            System.arraycopy(v23, 0, v3, 0, v23.length);
        } else {
            v4 = v3.length - v24;
            for (int i = v4; i <= v3.length; i++) {
                if (i == v3.length) {
                    i = 0;
                    v25 = 0;
                }
                v3[i] = v23[v25];
                if (v25 == v5) break;
                if (v5 == -1 && v25 == 0) break;
                v25++;
            }
        }
    }
}
