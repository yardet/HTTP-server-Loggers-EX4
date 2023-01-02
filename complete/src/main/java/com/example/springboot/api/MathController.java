package com.example.springboot.api;



import ch.qos.logback.classic.Level;
import com.example.springboot.model.mathPrase;
import com.example.springboot.service.mathPraseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class MathController {
	private final mathPraseService mathService1;



	private static final Logger requestLogger = LoggerFactory.getLogger("request-logger");
	private static final Logger stackLogger = LoggerFactory.getLogger("stack-logger");
	private static final Logger independentLogger = LoggerFactory.getLogger("independent-logger");
	private int requestNumber=1;

	Map<String, Object> map = new HashMap<>();

	//handle incoming requests function logger
	public void handleRequest(String resource, String verb){
		requestLogger.info("Incoming request | #"+requestNumber+" | resource: "+resource+" | HTTP Verb "+verb.toUpperCase() +" | request #"+requestNumber);
		requestNumber++;
	}
	//handle request duration function logger
	public void handleRequestDuration(long duration){
		requestLogger.debug("request #"+(requestNumber-1)+" duration: "+duration+"ms"+" | request #"+(requestNumber-1));
	}


	@Autowired
	public MathController(mathPraseService mathService1) {
		this.mathService1 = mathService1;

	}

	//1
	@PostMapping("/independent/calculate")
	public Map<String,? extends Object> addMath(@RequestBody mathPrase mathPrase1, HttpServletResponse response) {
		handleRequest("/independent/calculate","POST");
		map.clear();
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);

		ArrayList<Integer> arguments=mathPrase1.getArguments().stream().mapToInt(i->i).collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
		mathPrase matPrase2=new mathPrase(arguments,mathPrase1.getOperator());
		mathService1.addMath(matPrase2);
		if((arguments.size()<2 && !(mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("abs")
				|| mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("fact")))
				||
				(arguments.size()<1 &&
						( (mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("fact"))
								|| (mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("abs")))))
		{
			mathService1.responsePrase1.setError_message("Error: Not enough arguments to perform the operation"+mathPrase1.getOperator()+"\n");
			mathService1.responsePrase1.setResult(null);
			response.setStatus(409);
			independentLogger.error("Server encountered an error ! message: "+mathService1.responsePrase1.getError_message()+" | request #"+(requestNumber-1));
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if(requestLogger.isDebugEnabled())
				handleRequestDuration(duration);
			map.put("error-message",mathService1.responsePrase1.getError_message());
			return map;
			//return Map.of("error-message", mathService1.responsePrase1.getError_message());
		}

		else if((arguments.size()>2 && !(mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("abs")||
				mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("fact")))
				||
				(arguments.size()>1 &&
						( (mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("fact")) || (mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("abs"))))){
			mathService1.responsePrase1.setError_message("“Error: Too many arguments to perform the operation"+mathPrase1.getOperator()+"\n");
			mathService1.responsePrase1.setResult(null);
			response.setStatus(409);
			independentLogger.error("Server encountered an error ! message: "+mathService1.responsePrase1.getError_message()+" | request #"+(requestNumber-1));
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if(requestLogger.isDebugEnabled())
				handleRequestDuration(duration);
			map.put("error-message", mathService1.responsePrase1.getError_message());
			return map;
			//return Map.of("error-message", mathService1.responsePrase1.getError_message());
		}
		else
		{
			if(mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("abs") || mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("fact"))
				mathService1.responsePrase1=mathService1.calculation(arguments.get(0),arguments.get(0),mathPrase1.getOperator());
			else
				mathService1.responsePrase1=mathService1.calculation(arguments.get(0),arguments.get(1),mathPrase1.getOperator());

			if(mathService1.responsePrase1.getResult()==409){
				response.setStatus(409);
				mathService1.responsePrase1.setResult(null);
				independentLogger.error("Server encountered an error ! message: "+mathService1.responsePrase1.getError_message()+" | request #"+(requestNumber-1));
				endTime = System.currentTimeMillis();
				duration = (endTime - startTime);
				if(requestLogger.isDebugEnabled())
					handleRequestDuration(duration);
				map.put("error-message", mathService1.responsePrase1.getError_message());
				return map;
				//return Map.of("error-message", mathService1.responsePrase1.getError_message());
			}
			else{
				response.setStatus(200);
				independentLogger.info("Performing operation "+mathPrase1.getOperator()+". Result is "+mathService1.responsePrase1.getResult()+" | request #"+(requestNumber-1));
				if(mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("abs") || mathPrase1.getOperator().toLowerCase(Locale.ROOT).equals("fact")){
					if(independentLogger.isDebugEnabled())
						independentLogger.debug("Performing operation: "+mathPrase1.getOperator()+"("+arguments.get(0)+") = "+mathService1.responsePrase1.getResult()+" | request #"+(requestNumber-1));}
				else{
					if(independentLogger.isDebugEnabled())
						independentLogger.debug("Performing operation: "+mathPrase1.getOperator()+"("+arguments.get(0)+","+arguments.get(1)+") = "+mathService1.responsePrase1.getResult()+" | request #"+(requestNumber-1));}

				endTime = System.currentTimeMillis();
				duration = (endTime - startTime);
				if(requestLogger.isDebugEnabled())
					handleRequestDuration(duration);
				map.put("result", mathService1.responsePrase1.getResult());
				return map;
				//return Map.of("result", mathService1.responsePrase1.getResult());
			}
		}
	}

	@GetMapping
	public List<mathPrase> getAllMath(){
		return mathService1.getAllMath();
	}

	//2

	@GetMapping("/stack/size")
		public Map<String, Object> getStackSize(HttpServletResponse response){
		map.clear();
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		handleRequest("/stack/size","GET");//INFO

		response.setStatus(200);
		stackLogger.info("Stack size is: "+mathService1.getStackSize()+" | request #"+(requestNumber-1));
		stackLogger.debug("Stack content (first == top): "+mathService1.getArgumants()+" | request #"+(requestNumber-1));
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		if(requestLogger.isDebugEnabled())
			handleRequestDuration(duration);
		map.put("result", mathService1.getStackSize());
		return map;
		//return Map.of("result", mathService1.getStackSize());

	}


	//3
	@PutMapping("/stack/arguments")
	public Map<String, Object> addArgumants(@RequestBody mathPrase mathPrase1, HttpServletResponse response){
		map.clear();
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		handleRequest("/stack/arguments","PUT");
		ArrayList<Integer> arguments=mathPrase1.getArguments();
		mathService1.addArgumants(arguments);
		String argumantsStr=new String("");
		if(arguments.size()==1)
			argumantsStr=arguments.get(0).toString();
		else{
			for(int i=0;i<arguments.size();i++){
				argumantsStr=argumantsStr+arguments.get(i).toString();
				if(i!=arguments.size()-1)
					argumantsStr=argumantsStr+",";
			}
		}
		response.setStatus(200);
		stackLogger.info("Adding total of "+mathPrase1.getArguments().size()+" argument(s) to the stack | Stack size: "+mathService1.getStackSize()+" | request #"+(requestNumber-1));
		stackLogger.debug("Adding arguments: "+argumantsStr+" | Stack size before "+(mathService1.getStackSize()-mathPrase1.getArguments().size())+" | stack size after "+mathService1.getStackSize()+" | request #"+(requestNumber-1));

		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		if(requestLogger.isDebugEnabled())
			handleRequestDuration(duration);
		map.put("result", mathService1.getStackSize());
		return map;
		//return Map.of("result", mathService1.getStackSize());
	}

	//4
	@GetMapping("/stack/operate")
	public Map<String, Object> performOperation(@RequestParam String operation , HttpServletResponse response){
		map.clear();
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		handleRequest("/stack/operate","GET");
		int numOfArguments=2;
		if(operation.toLowerCase(Locale.ROOT).equals("abs")
				|| operation.toLowerCase(Locale.ROOT).equals("fact"))
			numOfArguments=1;
		if((mathService1.getStackSize()<2 && !(operation.toLowerCase(Locale.ROOT).equals("abs")
				|| operation.toLowerCase(Locale.ROOT).equals("fact")))
				||
				(mathService1.getStackSize()<1 &&
						( (operation.toLowerCase(Locale.ROOT).equals("fact"))
								|| (operation.toLowerCase(Locale.ROOT).equals("abs"))))){
			response.setStatus(409);
			stackLogger.error("Server encountered an error ! message: Error: cannot implement operation "+operation+". It requires "+ numOfArguments+  " arguments and the stack has only "+ mathService1.getStackSize()+ " arguments"+" | request #"+(requestNumber-1));
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if(requestLogger.isDebugEnabled())
				handleRequestDuration(duration);

			map.put("error-message","Error: cannot implement operation "+operation+"" +
					" It requires "+ numOfArguments+  " arguments and the stack has only "+ mathService1.getStackSize()+ " arguments");
			return map;
		}
		else{
			int a,b=0;
			if(numOfArguments==1){
				a=mathService1.getStackPop();
				mathService1.responsePrase1=mathService1.calculation(a,a,operation);}
			else{
				a=mathService1.getStackPop();
				b=mathService1.getStackPop();
				mathService1.responsePrase1=mathService1.calculation(a,b,operation);
			}
			if(mathService1.responsePrase1.getResult()==409){
				response.setStatus(409);
				stackLogger.error("Server encountered an error ! message: Error: cannot implement operation "+operation+". It requires "+ numOfArguments+  " arguments and the stack has only "+ mathService1.getStackSize()+ " arguments"+" | request #"+(requestNumber-1));
				endTime = System.currentTimeMillis();
				duration = (endTime - startTime);
				if(requestLogger.isDebugEnabled())
					handleRequestDuration(duration);
				//mathService1.responsePrase1.setResult(null);

				map.put("error-message", mathService1.responsePrase1.getError_message());
				return map;
			}
			else{
				response.setStatus(200);
				stackLogger.info("Performing operation "+operation+". Result is "+mathService1.responsePrase1.getResult().toString()+" | stack size: "+mathService1.getStackSize()+" | request #"+(requestNumber-1));

				if(numOfArguments==1){
					stackLogger.debug("Performing operation: "+operation+"("+a+") = "+mathService1.responsePrase1.getResult().toString()+" | request #"+(requestNumber-1));
				}
				else{
					stackLogger.debug("Performing operation: "+operation+"("+a+","+b+") = "+mathService1.responsePrase1.getResult().toString()+" | request #"+(requestNumber-1));
				}
				endTime = System.currentTimeMillis();
				duration = (endTime - startTime);
				if(requestLogger.isDebugEnabled())
					handleRequestDuration(duration);
				map.put("result", mathService1.responsePrase1.getResult());
				return map;
				//return Map.of("result", mathService1.responsePrase1.getResult());
			}

		}
	}

	//5
	@DeleteMapping( "/stack/arguments")
	public Map<String, Object> removeArgumants(@RequestParam int count, HttpServletResponse response){
		map.clear();
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		handleRequest("/stack/arguments","DELETE");
		if(count >mathService1.getStackSize()){
			response.setStatus(409);
			stackLogger.error("Server encountered an error ! message: “Error: Cannot remove "+count+" arguments from the stack. The stack has only "+mathService1.getStackSize()+" arguments\n"+" | request #"+(requestNumber-1));
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if(requestLogger.isDebugEnabled())
				handleRequestDuration(duration);

			map.put("error-message","Error: cannot remove "+ count + "from the stack. It has only "+mathService1.getStackSize()+" arguments in the stack> arguments");
			return map;
		}
		else{
			response.setStatus(200);
			stackLogger.info("Removing total "+count+" argument(s) from the stack | Stack size: "+(mathService1.getStackSize()-count)+" | request #"+(requestNumber-1));
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			mathService1.removeArgumants(count);
			if(requestLogger.isDebugEnabled())
				handleRequestDuration(duration);
			map.put("result", mathService1.getStackSize()-count);
			return map;
		}
	}

	//6
	@GetMapping("/logs/level")
	public String getLogLevel(@RequestParam("logger-name") String loggerName) {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		handleRequest("/logs/level","GET");
		Logger logger1 = LoggerFactory.getLogger(loggerName);
		if(logger1==null){
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if(requestLogger.isDebugEnabled())
				handleRequestDuration(duration);
			return "Error: Logger "+loggerName+" does not exist";
		}
		else{
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if(requestLogger.isDebugEnabled())
				handleRequestDuration(duration);
			return ((ch.qos.logback.classic.Logger) logger1).getLevel().levelStr.toUpperCase();
		}
	}

	//7
	@PutMapping("/logs/level")
	public String setLogLevel(@RequestParam("logger-name") String loggerName, @RequestParam("logger-level") String loggerLevel, HttpServletResponse response) {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		handleRequest("/logs/level", "PUT");
		Logger logger1 = LoggerFactory.getLogger(loggerName);
		if (logger1 == null) {
			endTime = System.currentTimeMillis();
			duration = (endTime - startTime);
			if(requestLogger.isDebugEnabled())
				handleRequestDuration(duration);
			return "Error: Logger " + loggerName + " does not exist";
		} else {
			if (loggerLevel.equalsIgnoreCase("ERROR")) {
				((ch.qos.logback.classic.Logger) logger1).setLevel(Level.ERROR);
				endTime = System.currentTimeMillis();
				duration = (endTime - startTime);
				if(requestLogger.isDebugEnabled())
					handleRequestDuration(duration);
				return ((ch.qos.logback.classic.Logger) logger1).getLevel().levelStr.toUpperCase();
			} else if (loggerLevel.equalsIgnoreCase("INFO")) {
				((ch.qos.logback.classic.Logger) logger1).setLevel(Level.INFO);
				endTime = System.currentTimeMillis();
				duration = (endTime - startTime);
				if(requestLogger.isDebugEnabled())
					handleRequestDuration(duration);
				return ((ch.qos.logback.classic.Logger) logger1).getLevel().levelStr.toUpperCase();
			} else if (loggerLevel.equalsIgnoreCase("DEBUG")) {
				((ch.qos.logback.classic.Logger) logger1).setLevel(Level.DEBUG);
				endTime = System.currentTimeMillis();
				duration = (endTime - startTime);
				if(requestLogger.isDebugEnabled())
					handleRequestDuration(duration);
				return ((ch.qos.logback.classic.Logger) logger1).getLevel().levelStr.toUpperCase();
			} else {
				endTime = System.currentTimeMillis();
				duration = (endTime - startTime);
				if(requestLogger.isDebugEnabled())
					handleRequestDuration(duration);
				return "Error: Level " + loggerLevel + " does not exist";
			}
		}
	}

}
