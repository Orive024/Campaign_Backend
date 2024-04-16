package com.orive.security.campaignDetails;



import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;



import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcelHelper {

	
	//check that file is of excel type or not
			public static boolean chechExcelFormat(MultipartFile file ) {
				
				String contentType = file.getContentType();
				
				if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				{
					return true;
				}
				else
				{
					return false;
				}
			}

			
			//convert excel to list of product
			
			public static List<CampaignDetails> convertExcelToListOfCampaignDetails(InputStream is){
				List<CampaignDetails> list = new ArrayList<>();
				
				try 
				{
					XSSFWorkbook workbook = new XSSFWorkbook(is);
					for(int i=0; i<workbook.getNumberOfSheets(); i++)
					{
						System.out.println("sheet name: " + workbook.getSheetName(i));
					}
					XSSFSheet sheet = workbook.getSheet("Sheet1");
					if(sheet == null)
					{
						System.out.println("sheet with name 'Sheet1' not found in the workbook");
					}
					int rowNumber=0;
					Iterator<Row> iterator = sheet.iterator();
					
					while (iterator.hasNext()) 
					{
						Row row = iterator.next();
						
						if(rowNumber==0)
						{
							rowNumber++;
							continue;
						}
						
					Iterator<Cell> cells = row.iterator();
					
					int cellId=0;
					
					CampaignDetails campaignDetails = new CampaignDetails();
					
					while(cells.hasNext()) 
					{
						Cell currentCell =  cells.next();
						
						switch (cellId) 
						{
	                         case 0:
	                        	campaignDetails.setCampaigndetailsid(getNumericValue(currentCell));
						     break;
                             case 1:
                    	        campaignDetails.setCampaignname(getStringValue(currentCell));
                             break;
                             case 2:
                            	 campaignDetails.setCampaigntype(getStringValue(currentCell));
                             break;
                             case 3:
                         	    campaignDetails.setCampaigndate(getStringValue(currentCell));
                              break;
                             case 4:
                         	    campaignDetails.setCampaignobjective(getStringValue(currentCell));
                              break;
                             case 5:
                        	    campaignDetails.setStarttime(getStringValue(currentCell));
                             break;
                             case 6:
                        	   campaignDetails.setEndtime(getStringValue(currentCell));
                             break;       
                             case 7:
                        	   campaignDetails.setLocation(getStringValue(currentCell));
                             break;
                             case 8:
                        	   campaignDetails.setCampaignbudget(getNumericValue(currentCell));
                             break;
                             case 9:
                        	   campaignDetails.setVolunteersassigned(getStringValue(currentCell));
                             break;
                             case 10:
                        	  campaignDetails.setCampaignmaterials(getStringValue(currentCell));
                             break;                                        
                            default:
                             break;
						}
						cellId++;
					}
					list.add(campaignDetails);
				}
					
					
				}catch (Exception attendanceDTO) 
				{
					attendanceDTO.printStackTrace();
				}
					return list;
				}
       

	
	
//methd for string conversion
private static String getStringValue(Cell cell) {
    if (cell.getCellType() == CellType.STRING) {
        return cell.getStringCellValue();
    } else if (cell.getCellType() == CellType.NUMERIC) {
        // Handle numeric values as needed
        return String.valueOf((int) cell.getNumericCellValue());
    } else {
        return null;
    }
}


//Method for localtime conversion	
//private static LocalTime getLocalTimeValue(Cell cell) {
//    if (cell.getCellType() == CellType.STRING) {
//        // Assuming the time is in string format (HH:mm:ss)
//        return LocalTime.parse(cell.getStringCellValue());
//    } else if (cell.getCellType() == CellType.NUMERIC) {
//        // Assuming the time is in numeric format (as a fraction of a day)
//        double numericValue = cell.getNumericCellValue();
//        return LocalTime.ofNanoOfDay((long) (numericValue * 24 * 60 * 60 * 1e9));
//    } else {
//        return null;
//    }
//}


//Method for numericvalue conversion	
private static Long getNumericValue(Cell cell) {
    if (cell.getCellType() == CellType.NUMERIC) {
        return (long) cell.getNumericCellValue();
    } else {
        return null;
    }
}


//Method for localdate conversion	
private static LocalDate getLocalDateValue(Cell cell) {
    if (cell.getCellType() == CellType.STRING) {
        // Assuming the date is in string format (yyyy-MM-dd)
        return LocalDate.parse(cell.getStringCellValue());
    } else if (cell.getCellType() == CellType.NUMERIC) {
        // Assuming the numeric value is a date representation
        return cell.getLocalDateTimeCellValue().toLocalDate();
    } else {
        return null;
    }
}


//Method for getByte conversion
private static byte[] getByteArrayValue(Cell cell) {
    if (cell.getCellType() == CellType.STRING) {
        // Assuming the cell content is a Base64-encoded string or other binary representation
        return Base64.getDecoder().decode(cell.getStringCellValue());
    } else {
        // Handle other cases as needed
        return null;
    }
  }
	
}