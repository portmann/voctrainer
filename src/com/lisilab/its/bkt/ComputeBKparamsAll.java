package com.lisilab.its.bkt;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adaptation of BKT-BF (www.columbia.edu/~rsb2162/BKT-BruteForce.zip)
 * 
 * This class expects data in chronological order but not sorted on Skill and
 * Student Free number of columns, order and separator
 * 
 * Parameters (1 is first): skillPos = Skill column position studentPos =
 * Student column position rightPos = Right column position separator =
 * Separator (comma(,) , tab(tab), etc.)
 * 
 * $> java ComputeKTparamsAll ./data.txt 4 3 6 tab
 */
public class ComputeBKparamsAll {
	public boolean lnminus1_estimation = false;
	public boolean bounded = true;
	public boolean L0Tbounded = false;

	private String inFile;
	private int studentPos, skillPos, rightPos;
	private char separator;

	private Map<String /* Skill */, Map<String /* Student */, List<Double> /* right */>> map = new HashMap<String, Map<String, List<Double>>>();

	public ComputeBKparamsAll(String inFile, int studentPos, int skillPos,
			int rightPos, char separator) {
		this.studentPos = studentPos;
		this.skillPos = skillPos;
		this.rightPos = rightPos;
		this.separator = separator;
		this.inFile = inFile;
	}

	public StreamTokenizer create_tokenizer() throws FileNotFoundException {
		StreamTokenizer st = new StreamTokenizer(new FileReader(inFile));

		st.resetSyntax();

		st.wordChars('\u0000', (char) (separator - 1));
		st.wordChars((char) (separator + 1), '\u00ff');
		st.whitespaceChars(separator, separator);
		st.whitespaceChars('\n', '\n');
		st.whitespaceChars('\r', '\r');

		return st;
	}

	public void read_in_data(StreamTokenizer st) throws IOException {
		int tt = 0;

		int columns = -1;
		do {
			tt = st.nextToken();
			columns++;
		} while (st.lineno() == 1);

		st.pushBack();

		boolean finish = false;

		while (true) {
			String student = null, skill = null;
			double right = 0;

			for (int col = 1; col <= columns; col++) {
				tt = st.nextToken();

				if (col == 1 && tt == StreamTokenizer.TT_EOF) {
					finish = true;
					break;
				}

				if (studentPos == col) {
					student = st.sval;
				} else if (skillPos == col) {
					skill = st.sval;
				} else if (rightPos == col) {
					right = Double.parseDouble(st.sval);
				}
			}

			if (!finish) {
				Map<String /* Student */, List<Double> /* right */> skillStudents = map
						.get(skill);
				if (skillStudents == null) {
					skillStudents = new HashMap<String, List<Double>>();
					map.put(skill, skillStudents);
				}

				List<Double> rightList = skillStudents.get(student);
				if (rightList == null) {
					rightList = new ArrayList<Double>();
					skillStudents.put(student, rightList);
				}

				rightList.add(right);
			} else {
				break;
			}
		}
	}

	public double findGOOF(String skill, double Lzero, double trans, double G,
			double S) {
		double SSR = 0.0;
		double prevL = 0.0;
		double likelihoodcorrect = 0.0;
		double prevLgivenresult = 0.0;
		double newL = 0.0;

		Map<String /* Student */, List<Double> /* right */> skillStudents = map
				.get(skill);
		for (Map.Entry<String, List<Double>> entry : skillStudents.entrySet()) {
			List<Double> rights = entry.getValue();

			prevL = Lzero;

			for (Double right : rights) {
				if (lnminus1_estimation)
					likelihoodcorrect = prevL;
				else
					likelihoodcorrect = (prevL * (1.0 - S))
							+ ((1.0 - prevL) * G);
				SSR += (right - likelihoodcorrect)
						* (right - likelihoodcorrect);

				if (right == 1.0)
					prevLgivenresult = ((prevL * (1.0 - S)) / ((prevL * (1 - S)) + ((1.0 - prevL) * (G))));
				else
					prevLgivenresult = ((prevL * (S)) / ((prevL * (S)) + ((1.0 - prevL) * (1.0 - G))));

				newL = prevLgivenresult + (1.0 - prevLgivenresult) * trans;
				prevL = newL;
			}
		}

		return SSR;
	}

	public void fit_skill_model(String skill) {
		double SSR = 0.0;
		double BestSSR = 9999999.0;
		double bestLzero = 0.01;
		double besttrans = 0.01;
		double bestG = 0.01;
		double bestS = 0.01;
		double topG = 0.99;
		double topS = 0.99;
		double topL0 = 0.99;
		double topT = 0.99;
		if (L0Tbounded) {
			topL0 = 0.85;
			topT = 0.3;
		}
		if (bounded) {
			topG = 0.3;
			topS = 0.1;
		}

		for (double Lzero = 0.01; Lzero <= topL0; Lzero = Lzero + 0.01)
			for (double trans = 0.01; trans <= topT; trans = trans + 0.01) {
				for (double G = 0.01; G <= topG; G = G + 0.01) {
					for (double S = 0.01; S <= topS; S = S + 0.01) {
						SSR = findGOOF(skill, Lzero, trans, G, S);
						/**
						 * System.out.print(Lzero); System.out.print("\t");
						 * System.out.println(trans);
						 */
						if (SSR < BestSSR) {
							BestSSR = SSR;
							bestLzero = Lzero;
							besttrans = trans;
							bestG = G;
							bestS = S;
						}
					}
				}
			}

		// for a bit mroe precision
		double startLzero = bestLzero;
		double starttrans = besttrans;
		double startG = bestG;
		double startS = bestS;
		for (double Lzero = startLzero - 0.009; ((Lzero <= startLzero + 0.009) && (Lzero <= topL0)); Lzero = Lzero + 0.001)
			for (double G = startG - 0.009; ((G <= startG + 0.009) && (G <= topG)); G = G + 0.001) {
				for (double S = startS - 0.009; ((S <= startS + 0.009) && (S <= topS)); S = S + 0.001) {
					for (double trans = starttrans - 0.009; ((trans <= starttrans + 0.009) && (trans < topT)); trans = trans + 0.001) {
						SSR = findGOOF(skill, Lzero, trans, G, S);
						if (SSR < BestSSR) {
							BestSSR = SSR;
							bestLzero = Lzero;
							besttrans = trans;
							bestG = G;
							bestS = S;
						}
					}
				}
			}

		System.out.print(skill);
		System.out.print("\t");
		System.out.print(bestLzero);
		System.out.print("\t");
		System.out.print(bestG);
		System.out.print("\t");
		System.out.print(bestS);
		System.out.print("\t");
		System.out.print(besttrans);
		System.out.println("\teol");
	}

	public void computelzerot() throws IOException {
		StreamTokenizer st = create_tokenizer();

		read_in_data(st);

		System.out.println("skill\tL0\tG\tS\tT\teol");
		for (String skill : map.keySet()) {
			fit_skill_model(skill);
		}
	}

	public static void main(String args[]) throws IOException {
		// args = new
		// String[]{"C:/Users/Isidro/Desktop/BigData Education/W4/BKT-BruteForce/TestData.txt","4","3","6","tab"};
		// args = new
		// String[]{"C:/Users/Isidro/Desktop/BigData Education/W4/isi_data2.txt","4","3","6","tab"};
		// args = new
		// String[]{"C:/Users/Isidro/Desktop/BigData Education/W4/isi_data3.txt","4","3","6",","};

		String inFile = args[0];
		int skillPos = Integer.parseInt(args[1]);
		int studentPos = Integer.parseInt(args[2]);
		int rightPos = Integer.parseInt(args[3]);
		char separator;

		if (args[4].equals("tab")) {
			separator = '\t';
		} else {
			separator = args[4].toCharArray()[0];
		}
		System.out.println("Skill Column: " + skillPos);
		System.out.println("Student Column: " + studentPos);
		System.out.println("Right Column: " + rightPos);
		System.out.println("Separator: " + args[4]);

		ComputeBKparamsAll m = new ComputeBKparamsAll(inFile, studentPos,
				skillPos, rightPos, separator);
		m.computelzerot();
	}
}