package org.apache.cordova.database;

public class Record_DataSet {
		private String Adate;
		private String Adata;
		
		public Record_DataSet(String Adate,String Adata){
			this.Adate=Adate;
			this.Adata=Adata;
		}
		
		public void setAdate(String Adate)
		{
			this.Adate=Adate;
		}
		
		public void setAdata(String Adata)
		{
			this.Adata=Adata;
		}
		
		public String getAdate()
		{
			return this.Adate;
		}
		
		public String getAdata()
		{
			return this.Adata;
		}
		

}
