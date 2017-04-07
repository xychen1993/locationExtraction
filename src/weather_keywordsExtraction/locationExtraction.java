package weather_keywordsExtraction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class locationExtraction {
	public String Tokens[];

	public static void main(String[] args)throws IOException, SAXException, TikaException{		
        String cnt="How's the weather in New York, Chicago?";
        
        //1. convert sentence into tokens
        locationExtraction toi = new locationExtraction();
        toi.tokenization(cnt);
        String names = toi.locationFind(toi.Tokens);

        System.out.println("location name is : "+names);

    }
	//2. find names
    public String locationFind(String cnt[]) {
    	InputStream is;
        TokenNameFinderModel tnf;
        NameFinderME nf;
        String sd = "";
        try {
            is = new FileInputStream(
                    "/Users/falcon/Documents/EclipseWorkspace/weather_keywordsExtraction/en-ner-location.bin");
        			//"/Users/falcon/Documents/EclipseWorkspace/weather_keywordsExtraction/es-ner-location.bin");
        			//"/Users/falcon/Documents/EclipseWorkspace/weather_keywordsExtraction/nl-ner-location.bin");
        			tnf = new TokenNameFinderModel(is);
            nf = new NameFinderME(tnf);

            Span sp[] = nf.find(cnt);

            String a[] = Span.spansToStrings(sp, cnt);
            StringBuilder fd = new StringBuilder();
            int l = a.length;

            for (int j = 0; j < l; j++) {
                fd = fd.append(a[j] + "\n");

            }
            sd = fd.toString();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return sd;
    }
    
    public void tokenization(String tokens) {

        InputStream is;
        TokenizerModel tm;

        try {
            is = new FileInputStream("/Users/falcon/Documents/EclipseWorkspace/weather_keywordsExtraction/en-token.bin");
            tm = new TokenizerModel(is);
            Tokenizer tz = new TokenizerME(tm);
            Tokens = tz.tokenize(tokens);
            // System.out.println(Tokens[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

