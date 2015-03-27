package com.utils;

import static com.jayway.restassured.filter.log.LogDetail.ALL;
import static com.jayway.restassured.filter.log.LogDetail.STATUS;

import java.io.PrintStream;

import org.apache.commons.lang3.Validate;
import org.testng.Reporter;

import com.jayway.restassured.filter.Filter;
import com.jayway.restassured.filter.FilterContext;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.internal.print.RequestPrinter;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.FilterableRequestSpecification;
import com.jayway.restassured.specification.FilterableResponseSpecification;

public class MyRequestLoggerFilter  implements Filter {

	private final LogDetail logDetail;
	private final PrintStream stream;
	private final boolean shouldPrettyPrint;

	public MyRequestLoggerFilter(PrintStream stream) {
		this(ALL, stream);
	}

	public MyRequestLoggerFilter() {
		this(ALL, System.out);
	}

	public MyRequestLoggerFilter(LogDetail logDetail, PrintStream stream) {
		this(logDetail, true, stream);
	}

	public Response filter(FilterableRequestSpecification requestSpec,
			FilterableResponseSpecification responseSpec, FilterContext ctx) {
		RequestPrinter.print(requestSpec, ctx.getRequestMethod().toString(),
				ctx.getCompleteRequestPath(), logDetail, stream,
				shouldPrettyPrint);
		info(ctx.getCompleteRequestPath());
		return ctx.next(requestSpec, responseSpec);
	}

	public MyRequestLoggerFilter(LogDetail logDetail,
			boolean shouldPrettyPrint, PrintStream stream) {

		Validate.notNull(stream, "Print stream cannot be null");
		Validate.notNull(logDetail, "Log details cannot be null");
		if (logDetail == STATUS) {
			throw new IllegalArgumentException(String.format(
					"%s is not a valid %s for a request.", STATUS,
					LogDetail.class.getSimpleName()));
		}
		this.stream = stream;
		this.logDetail = logDetail;
		this.shouldPrettyPrint = shouldPrettyPrint;
	}
	
	public void info(String info){
		Reporter.log(info);
	}

}
