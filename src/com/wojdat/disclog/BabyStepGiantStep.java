package com.wojdat.disclog;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class BabyStepGiantStep {

    static long count = 1048576;
    static BigInteger p = new BigInteger("13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084171");
    static BigInteger g = new BigInteger("26790476600736210439911326117339978371955050182095051707060863109260114006743503487596200818701323671541681934177621901008556022090467034705794892743945893");
    static BigInteger h = new BigInteger("4476678147226155115558365457750475595861521772603596936757528326067178243549776543712192144627870184928567432208656624794079113374237487568153440925199271");


    public static Map<BigInteger, Long> getHashMap() {
        BigInteger gPow, gInversePow, n;
        Map<BigInteger, Long> result = new HashMap<>();
        for (long i = 0; i < count; i++) {
            gPow = g.modPow(new BigInteger(i + ""), p);
            gInversePow = gPow.modInverse(p);
            n = h.multiply(gInversePow);
            n = n.mod(p);
            result.put(n, i);
        }
        return result;
    }


    public static long getDiscLog(Map<BigInteger, Long> hashMap) {
        BigInteger n;
        long result = 0;
        BigInteger gPowCount = g.modPow(new BigInteger(count + ""), p);
        for (long i = 0; i < count; i++) {
            n = gPowCount.modPow(new BigInteger(i + ""), p);
            if (hashMap.containsKey(n)) {
                result = i * count + hashMap.get(n);
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Map<BigInteger, Long> hashMap = getHashMap();
        long log = getDiscLog(hashMap);
        System.out.println("log: " + log);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("time: " + totalTime / 1000 + " seconds");
        BigInteger power = g.modPow(new BigInteger(log + ""), p);
        System.out.println("g^x mod p =  " + power);
        System.out.println("is it equal to given h? " + power.equals(h));
    }

}