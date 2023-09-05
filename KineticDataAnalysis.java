import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KineticDataAnalysis {

    private static String RateLaw;
    private static int reactionOrder;
    private static ArrayList<String[]> Data;

    public static String getRateLaw() {
        return RateLaw;
    }



    public static void PutData(JComboBox dataType, String text)
    {
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        Scanner s = new Scanner(text);
        if(dataType.getSelectedItem()=="[A] [B] Initial Rate")
        {
            while(s.hasNextLine())
            {
                s.useDelimiter("\\s+");

                String firstReactantCon = s.next();
                if(firstReactantCon.contains("E"))
                    firstReactantCon = EvalString.FromScientificToDouble(firstReactantCon);

                String secondReactantCon = s.next();
                if(secondReactantCon.contains("E"))
                    secondReactantCon = EvalString.FromScientificToDouble(secondReactantCon);

                String initialRate = s.next();
                if(initialRate.contains("E"))
                    initialRate = EvalString.FromScientificToDouble(initialRate);


                String data[] = {firstReactantCon,secondReactantCon,initialRate};
                arrayList.add(data);

            }
            Data = arrayList;
            FindRateLawFromGivenInitialRate(arrayList,2);

        }else if(dataType.getSelectedItem()=="[A] Initial Rate") {
            while(s.hasNextLine())
            {
                s.useDelimiter("\\s+");

                String firstReactantCon = s.next();
                if(firstReactantCon.contains("E"))
                    firstReactantCon = EvalString.FromScientificToDouble(firstReactantCon);

                String initialRate = s.next();
                if(initialRate.contains("E"))
                    initialRate = EvalString.FromScientificToDouble(initialRate);

                String data[] = {firstReactantCon, initialRate};
                arrayList.add(data);

            }
            Data = arrayList;
            FindRateLawFromGivenInitialRate(arrayList,1);
        }else if(dataType.getSelectedItem()=="Time [A]"){
            while(s.hasNext()) {
                s.useDelimiter("\\s+");

                String time = s.next();
                if (time.contains("E"))
                    time = EvalString.FromScientificToDouble(time);

                String concentration = s.next();
                if (concentration.contains("E"))
                    concentration = EvalString.FromScientificToDouble(concentration);

                String data[] = {time, concentration};
                arrayList.add(data);
            }
            Data = arrayList;
            //graphData(arrayList);
        }

    }





    private static int[] FindDataWithEqualData(ArrayList<String[]> al, int whatData)
    {
        //Fine equal [] in set of data
        for(int i = 0; i<al.size(); i++)
        {
            String si = al.get(i)[whatData];
            for(int j = 0; j<al.size();j++)
            {
                String sj = al.get(j)[whatData];
                if(i!=j)
                {
                    if(si.equals(sj))
                    {
                        return new int[]{i,j};
                    }
                }
            }

        }
        return new int[]{0,1};
    }


    public static void FindRateLawFromGivenInitialRate(ArrayList<String[]> al, int numberOfReactas) {


        if (numberOfReactas == 1) {
            int dataSetsWithEqualData[] = FindDataWithEqualData(al, 0);
            //System.out.println("" + dataSetsWithEqualData[0] + dataSetsWithEqualData[1]);

            double molarity1 = Double.parseDouble(al.get(dataSetsWithEqualData[0])[0]);
            double molarity2 = Double.parseDouble(al.get(dataSetsWithEqualData[1])[0]);
            double rate1 = Double.parseDouble(al.get(dataSetsWithEqualData[0])[1]);
            double rate2 = Double.parseDouble(al.get(dataSetsWithEqualData[1])[1]);

            //multipInMolarity^x=multipInRate
            double multipInMolarity = molarity2 / molarity1;
            double multipInRate = rate2 / rate1;

            int order = (int) Math.round(Math.log10(multipInRate) / Math.log10(multipInMolarity));

            String rateLaw = "k[A]^" + order;
            reactionOrder = order;
            RateLaw = rateLaw;

            //System.out.print(RateLaw);

        } else {
            //order for first reactant
            int dataSetsWithEqualData[] = FindDataWithEqualData(al, 1);

            double molarity1 = Double.parseDouble(al.get(dataSetsWithEqualData[0])[0]);
            double molarity2 = Double.parseDouble(al.get(dataSetsWithEqualData[1])[0]);
            double rate1 = Double.parseDouble(al.get(dataSetsWithEqualData[0])[2]);
            double rate2 = Double.parseDouble(al.get(dataSetsWithEqualData[1])[2]);

            double multipInMolarity = molarity2 / molarity1;
            double multipInRate = rate2 / rate1;

            int reactant1_order = (int) Math.round(Math.log10(multipInRate) / Math.log10(multipInMolarity));

            //order for second reactant
            dataSetsWithEqualData = FindDataWithEqualData(al, 0);

            molarity1 = Double.parseDouble(al.get(dataSetsWithEqualData[0])[1]);
            molarity2 = Double.parseDouble(al.get(dataSetsWithEqualData[1])[1]);
            rate1 = Double.parseDouble(al.get(dataSetsWithEqualData[0])[2]);
            rate2 = Double.parseDouble(al.get(dataSetsWithEqualData[1])[2]);

            multipInMolarity = molarity2 / molarity1;
            multipInRate = rate2 / rate1;

            int reactant2_order = (int) Math.round(Math.log10(multipInRate) / Math.log10(multipInMolarity));

            String rateLaw = "k([A]^" + reactant1_order + ")*([B]^" + reactant2_order + ")";
            reactionOrder=reactant1_order+reactant2_order;
            RateLaw = rateLaw;
        }

    }

    public static double solveForK(String rl, JComboBox choice)
    {
        rl = rl.replace("k","");
        if(choice.getSelectedItem()=="[A] [B] Initial Rate" || choice.getSelectedItem()=="[A] Initial Rate")
        {
            if(choice.getSelectedItem()=="[A] [B] Initial Rate") {
                String AReactantMolarity = Data.get(0)[0];
                String BReactantMolarity = Data.get(0)[1];
                String rate = Data.get(0)[2];

                rl = rl.replace("[A]", "(" + AReactantMolarity + ")");
                rl = rl.replace("[B]", "(" + BReactantMolarity + ")");

                double k = EvalString.eval(rate + "/(" + rl + ")");
                return k;

            }

            if(choice.getSelectedItem()=="[A] Initial Rate"){
                String AReactantMolarity = Data.get(0)[0];
                String rate = Data.get(0)[1];

                rl = rl.replace("[A]", "(" + AReactantMolarity + ")");

                double k = EvalString.eval(rate + "/(" + rl + ")");
                return k;
            }
        }
        return Double.parseDouble(null);
    }

    public static ArrayList<Double[]> addLnAndInvToData()
    {
        ArrayList<Double[]> newAl = new ArrayList<Double[]>();
        for(int i = 0; i <Data.size(); i++)
        {
            double time = Double.parseDouble(Data.get(i)[0]);
            double consentration = Double.parseDouble(Data.get(i)[1]);

            double CLn = Math.log(consentration);
            double CIv = 1/consentration;

            Double data[] = {time, consentration, CLn, CIv};

            newAl.add(data);
        }
        return newAl;
    }


}
