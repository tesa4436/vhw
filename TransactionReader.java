import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionReader {

	public List<Transaction> read(String filePath) throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			var transactions = new ArrayList<Transaction>();

			while ((line = br.readLine()) != null) {
				String[] columns = line.split(" ");

				if (columns.length == 3) { //just some code to parse the columns
					String dateString = columns[0];
					String string1 = columns[1];
					String string2 = columns[2];
					LocalDate date;
					PackageSize size;
					Provider provider;

					try {
						date = LocalDate.parse(dateString);
					} catch (DateTimeParseException e ) {
						transactions.add(new Transaction(line + " Ignored"));
						continue; // create an invalid transaction. used for output to stdout
					}

					try {
						size = PackageSize.valueOf(string1);
						provider = Provider.valueOf(string2);
					} catch (IllegalArgumentException e) {
						transactions.add(new Transaction(line + " Ignored"));
						continue; // create an invalid transaction
					}

					var transaction = new Transaction(date, size, provider);
					transactions.add(transaction);
				} else {
					transactions.add(new Transaction(line + " Ignored"));
				} // create an invalid transaction
			}

			return transactions;
		}
	}
}
