package com.demo.batchReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class itemReader implements Tasklet {

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Path firstfilepath = Paths.get("C:\\A");
		Path secondfilepath = Paths.get("C:\\B");
		try {
			List<Path> csvFiles = Files.list(firstfilepath)
					.filter(path -> path.toString().toLowerCase().endsWith(".csv")).collect(Collectors.toList());
			for (Path csvFile : csvFiles) {
				Path targetfilepath = secondfilepath.resolve(csvFile.getFileName());
				Files.move(csvFile, targetfilepath, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Moved FROM FOLDER A => B");
				Files.delete(csvFile);
			}
		} catch (IOException e) {
			System.err.println("moving or deleting the file: " + e.getMessage());
		}

		return null;
	}
}