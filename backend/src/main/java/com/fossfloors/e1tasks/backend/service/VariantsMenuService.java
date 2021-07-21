package com.fossfloors.e1tasks.backend.service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fossfloors.e1tasks.backend.beans.VariantID;
import com.fossfloors.e1tasks.backend.entity.Task;
import com.fossfloors.e1tasks.backend.util.TaskMenuPrintUtil;
import com.fossfloors.e1tasks.backend.util.TaskRelationshipsProcessor;

//@Service
//@Transactional
public class VariantsMenuService {

  private static final Logger           logger = LoggerFactory.getLogger(VariantsMenuService.class);

  private final TaskMasterService       taskMasterService;
  private final TaskRelationshipService relationService;
  private final VariantDetailService    variantService;

  private TaskRelationshipsProcessor    relationsProcessor;

  private Map<VariantID, Task>          fossVariantsMap;

  // private Map<String, LinkedHashSet<Task>> masterMenuMap;
  // private Map<VariantID, LinkedHashSet<Task>> variantMenuMap;

  @Autowired
  public VariantsMenuService(TaskMasterService taskMasterService,
      TaskRelationshipService relationService, VariantDetailService variantService) {
    this.taskMasterService = taskMasterService;
    this.relationService = relationService;
    this.variantService = variantService;
  }

  @PostConstruct
  private void init() {
    relationsProcessor = new TaskRelationshipsProcessor(taskMasterService.findAll(),
        relationService, variantService);

    Task fossTask = relationsProcessor.buildMasterMenu("1014");
    TaskMenuPrintUtil.printMasterMenu("1014", fossTask);

    fossVariantsMap = relationsProcessor.buildVariantMenusForView("1014");

    // Task ffmfgloTask = relationsProcessor.buildVariantMenu("FFMFGLO", "1014");
    // TaskMenuPrintUtil.printVariantMenu(new VariantID("FFMFGLO", "1014"), ffmfgloTask);
    //
    // Task ffmathanTask = relationsProcessor.buildVariantMenu("FFMATHAN", "1014");
    // TaskMenuPrintUtil.printVariantMenu(new VariantID("FFMATHAN", "1014"), ffmathanTask);
    //
    // Task ffconvmgrTask = relationsProcessor.buildVariantMenu("FFCONVMGR", "1014");
    // TaskMenuPrintUtil.printVariantMenu(new VariantID("FFCONVMGR", "1014"), ffconvmgrTask);
  }

  public List<String> getMainMenuTaskViews() {
    return relationService.getDistinctTaskViewIDs();
  }

}
