package Main;

import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

/**
 *
 * @author Nazgul
 */
public class Codigos {

    protected PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
    protected PanamaHitek_MultiMessage multi = new PanamaHitek_MultiMessage(2, arduino);

    protected SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {

        }
    };

    public int[] GenerarCodigoHamming(int[] x) {
        int[] codH = new int[11];
        int p1, p2, p3, p4;

        p1 = x[0] ^ x[1] ^ x[3] ^ x[4] ^ x[6];
        p2 = x[0] ^ x[2] ^ x[3] ^ x[5] ^ x[6];
        p3 = x[1] ^ x[2] ^ x[3];
        p4 = x[4] ^ x[5] ^ x[6];

        int j = 0;
        for (int i = 0; i < codH.length; i++) {
            switch (i) {
                case 0:
                    codH[i] = p1;
                    break;
                case 1:
                    codH[i] = p2;
                    break;
                case 3:
                    codH[i] = p3;
                    break;
                case 7:
                    codH[i] = p4;
                    break;
                default:
                    codH[i] = x[j];
                    j++;
                    break;
            }
        }
        return codH;
    }

    public boolean Verificar(int[] x) {
        int[] arrayPv = new int[4];
        int[] arrayP = new int[4];
        arrayPv[0] = x[0];
        arrayPv[1] = x[1];
        arrayPv[2] = x[3];
        arrayPv[3] = x[7];

        arrayP[0] = x[2] ^ x[4] ^ x[6] ^ x[8] ^ x[10];
        arrayP[1] = x[2] ^ x[5] ^ x[6] ^ x[9] ^ x[10];
        arrayP[2] = x[4] ^ x[5] ^ x[6];
        arrayP[3] = x[8] ^ x[9] ^ x[10];

        for (int i = 0; i < 4; i++) {
            if (arrayPv[i] != arrayP[i]) {
                return false;
            }
        }
        return true;

    }

    public int PosBit(int[] x) {
        int[] arrayPv = new int[4];
        int[] arrayP = new int[4];
        int[] arrayBit = new int[4];
        arrayPv[0] = x[0];
        arrayPv[1] = x[1];
        arrayPv[2] = x[3];
        arrayPv[3] = x[7];

        arrayP[0] = x[2] ^ x[4] ^ x[6] ^ x[8] ^ x[10];
        arrayP[1] = x[2] ^ x[5] ^ x[6] ^ x[9] ^ x[10];
        arrayP[2] = x[4] ^ x[5] ^ x[6];
        arrayP[3] = x[8] ^ x[9] ^ x[10];

        for (int i = 0; i < 4; i++) {
            arrayBit[i] = arrayPv[i] ^ arrayP[i];
        }
        return 8 * arrayBit[3] + 4 * arrayBit[2] + 2 * arrayBit[1] + 1 * arrayBit[0];
    }

    public void VerArray(int[] x) {
        System.out.println("Array:");
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i] + " ");
        }
        System.out.println();
    }
}
