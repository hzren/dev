package zhaocai.ciphersuites;

import java.security.Provider;
import java.security.Security;
import java.util.Properties;

public class Main
{

	public static void main(String[] args)
	{
		Properties properties = System.getProperties();
		properties.entrySet();

		try
		{
			getAlgothrims();

		} finally
		{
			System.out.println();
		}
	}

	static String getAlgothrims()
	{
		System.out.println(System.getProperty("https.cipherSuites"));

		for (Provider provider : Security.getProviders())
		{
			System.out.println("Provider: " + provider.getName());
			for (Provider.Service service : provider.getServices())
			{
				System.out.println("  Algorithm: " + service.getAlgorithm());
			}
		}

		return "a";
	}
}
