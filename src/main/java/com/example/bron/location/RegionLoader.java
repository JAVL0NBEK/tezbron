package com.example.bron.location;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegionLoader implements CommandLineRunner {
  private final RegionComponent regionComponent;

  @Override
  public void run(String... args) {
    //regionComponent.importRegions();
  }
}
