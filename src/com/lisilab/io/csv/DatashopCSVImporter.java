package com.lisilab.io.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lisilab.its.TransactionBuilder;
import com.lisilab.its.model.DatashopTransactionBuilder;
import com.lisilab.its.model.Transaction;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;

public class DatashopCSVImporter {

	public List<Transaction> importData(String file) {

		List<Transaction> result = new ArrayList<Transaction>();

		CSV csv = CSV.separator('	') // delimiter of fields
				.quote('"') // quote character
				.create(); // new instance is immutable

		CSVReadProc readProc = new DataShopCSVRead();

		csv.read("example.csv", new CSVReadProc() {
			public void procRow(int rowIndex, String... values) {
				System.out.println(rowIndex + ": " + Arrays.asList(values));
			}
		});

		return result;
	}

	class DataShopCSVRead<T> implements CSVReadProc {

		private List<String> indexRow;
		private Map<String, ColumnHandler> column2HandlerMapping = column2HandlerMapping();
		private TransactionBuilder builder = new DatashopTransactionBuilder();
		private List<Transaction> transactions = new ArrayList<Transaction>();

		@Override
		public void procRow(int rowIndex, String... values) {

			if (rowIndex == 0) {
				indexRow = Arrays.asList(values);
			} else {

				for (int columnIndex = 0; columnIndex < values.length; columnIndex++) {
					ColumnHandler handler = column2HandlerMapping.get(indexRow
							.get(columnIndex));
					handler.processValue(builder, values[columnIndex]);
				}
			}

			Transaction transaction = builder.getTransaction();
			transactions.add(transaction);
			builder.newTransaction();

			// TODO Auto-generated method stub

		}

		public Map<String, ColumnHandler> column2HandlerMapping() {

			Map<String, ColumnHandler> handlerMapping = new HashMap<String, ColumnHandler>();

			handlerMapping.put("Transaction Id", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setTransactionId(value);
				}

			});

			handlerMapping.put("Anon Student Id", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setStudent(value);
				}

			});

			handlerMapping.put("Session Id", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setSessionId(value);
				}

			});

			handlerMapping.put("Time", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setTime(new Date(value));
				}

			});

			handlerMapping.put("Time Zone", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					// TODO
				}

			});

			handlerMapping.put("Duration (sec)", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setDuration(Integer.parseInt(value));
				}

			});

			handlerMapping.put("Student Response Type", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setStudentResponseType(value);
				}

			});
			handlerMapping.put("Student Response Subtype", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setStudentResponseSubType(value);
				}

			});
			handlerMapping.put("Tutor Response Type", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setTutorResponseType(value);
				}

			});
			handlerMapping.put("Tutor Response Subtype", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setStudentResponseSubType(value);
				}

			});
			handlerMapping.put("Level (Unit)", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					// TODO Auto-generated method stub

				}

			});
			handlerMapping.put("Level (Section)", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					// TODO Auto-generated method stub

				}

			});
			handlerMapping.put("Problem Name", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setProblemName(value);
				}

			});
			handlerMapping.put("Problem View", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					// TODO Auto-generated method stub

				}

			});
			handlerMapping.put("Problem Start Time", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					// TODO
				}

			});
			handlerMapping.put("Step Name", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setStepName(value);
				}

			});
			handlerMapping.put("Attempt At Step", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					// TODO
				}

			});
			handlerMapping.put("Outcome", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setOutcome(value);
				}

			});
			handlerMapping.put("Selection", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					// TODO
				}

			});
			handlerMapping.put("Action", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setAction(value);
				}

			});
			handlerMapping.put("Input", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setInput(value);
				}

			});
			handlerMapping.put("Feedback Text", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setFeedbackText(value);
				}

			});
			handlerMapping.put("Feedback Classification", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setFeedbackClassification(value);
				}

			});
			handlerMapping.put("Help Level", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setHelpLevel(value);
				}

			});

			handlerMapping.put("Total Num Hints", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setTotalNumHints(value);
				}

			});

			// KC (Single-KC),KC Category (Single-KC),KC
			// (Unique-step),KC Category (Unique-step),School,Class

			handlerMapping.put("KC (Default)", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setKC(value);
				}

			});

			handlerMapping.put("KC Category (Default)", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setKCCategory(value);
				}

			});

			handlerMapping.put("KC (Single-KC)", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setKCSingleKC(value);
				}

			});

			handlerMapping.put("KC Category (Single-KC)", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setKCCategorySingleKC(value);
				}

			});

			handlerMapping.put("KC (Unique-step)", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setKCUniqueStep(value);
				}

			});

			handlerMapping.put("KC Category (Unique-step)",
					new ColumnHandler() {

						@Override
						public void processValue(TransactionBuilder builder,
								String value) {
							builder.setKCCategoryUniqueStep(value);
						}

					});

			handlerMapping.put("School", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setSchool(value);
				}

			});

			handlerMapping.put("Class", new ColumnHandler() {

				@Override
				public void processValue(TransactionBuilder builder,
						String value) {
					builder.setClass(value);
				}

			});

			return handlerMapping;
		}

	}

	abstract class ColumnHandler {

		public abstract void processValue(TransactionBuilder builder,
				String value);

	}
}
