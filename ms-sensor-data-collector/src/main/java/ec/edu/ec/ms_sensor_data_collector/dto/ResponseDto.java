package ec.edu.ec.ms_sensor_data_collector.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
	private String message;
	private Object data;
}
