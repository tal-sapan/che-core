/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.core;

import com.google.gwt.resources.client.ClientBundle;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import org.eclipse.che.api.project.shared.Constants;
import org.eclipse.che.ide.Resources;
import org.eclipse.che.ide.actions.CloseProjectAction;
import org.eclipse.che.ide.actions.CollapseAllAction;
import org.eclipse.che.ide.actions.CopyAction;
import org.eclipse.che.ide.actions.CreateModuleAction;
import org.eclipse.che.ide.actions.CutAction;
import org.eclipse.che.ide.actions.DeleteItemAction;
import org.eclipse.che.ide.actions.DownloadItemAction;
import org.eclipse.che.ide.actions.DownloadProjectAsZipAction;
import org.eclipse.che.ide.actions.ExpandAllAction;
import org.eclipse.che.ide.actions.ExpandEditorAction;
import org.eclipse.che.ide.actions.FindReplaceAction;
import org.eclipse.che.ide.actions.FoldersAlwaysOnTopAction;
import org.eclipse.che.ide.actions.FormatterAction;
import org.eclipse.che.ide.actions.GoIntoAction;
import org.eclipse.che.ide.actions.ImportLocalProjectAction;
import org.eclipse.che.ide.actions.ImportProjectFromLocationAction;
import org.eclipse.che.ide.actions.NavigateToFileAction;
import org.eclipse.che.ide.actions.NewProjectAction;
import org.eclipse.che.ide.actions.OpenFileAction;
import org.eclipse.che.ide.actions.OpenProjectAction;
import org.eclipse.che.ide.actions.ExpandNodeAction;
import org.eclipse.che.ide.actions.OpenSelectedFileAction;
import org.eclipse.che.ide.actions.PasteAction;
import org.eclipse.che.ide.actions.ProjectConfigurationAction;
import org.eclipse.che.ide.actions.RedoAction;
import org.eclipse.che.ide.actions.RenameItemAction;
import org.eclipse.che.ide.actions.SaveAction;
import org.eclipse.che.ide.actions.SaveAllAction;
import org.eclipse.che.ide.actions.ShowHiddenFilesAction;
import org.eclipse.che.ide.actions.ShowPreferencesAction;
import org.eclipse.che.ide.actions.UndoAction;
import org.eclipse.che.ide.actions.UploadFileAction;
import org.eclipse.che.ide.actions.UploadFolderFromZipAction;
import org.eclipse.che.ide.actions.find.FindActionAction;
import org.eclipse.che.ide.api.action.ActionManager;
import org.eclipse.che.ide.api.action.DefaultActionGroup;
import org.eclipse.che.ide.api.action.IdeActions;
import org.eclipse.che.ide.api.editor.EditorRegistry;
import org.eclipse.che.ide.api.filetypes.FileType;
import org.eclipse.che.ide.api.filetypes.FileTypeRegistry;
import org.eclipse.che.ide.api.icon.Icon;
import org.eclipse.che.ide.api.icon.IconRegistry;
import org.eclipse.che.ide.api.keybinding.KeyBindingAgent;
import org.eclipse.che.ide.api.keybinding.KeyBuilder;
import org.eclipse.che.ide.connection.WsConnectionListener;
import org.eclipse.che.ide.imageviewer.ImageViewerProvider;
import org.eclipse.che.ide.newresource.NewFileAction;
import org.eclipse.che.ide.newresource.NewFolderAction;
import org.eclipse.che.ide.ui.toolbar.MainToolbar;
import org.eclipse.che.ide.ui.toolbar.ToolbarPresenter;
import org.eclipse.che.ide.xml.NewXmlFileAction;
import org.vectomatic.dom.svg.ui.SVGResource;

import static org.eclipse.che.ide.api.action.IdeActions.GROUP_FILE_NEW;

/**
 * Initializer for standard components i.e. some basic menu commands (Save, Save As etc)
 *
 * @author Evgen Vidolob
 * @author Dmitry Shnurenko
 */
@Singleton
public class StandardComponentInitializer {
    public interface ParserResource extends ClientBundle {
        @Source("org/eclipse/che/ide/blank.svg")
        SVGResource samplesCategoryBlank();
    }

    @Inject
    private EditorRegistry editorRegistry;

    @Inject
    private FileTypeRegistry fileTypeRegistry;

    @Inject
    private Resources resources;

    @Inject
    private KeyBindingAgent keyBinding;

    @Inject
    private ActionManager actionManager;

    @Inject
    private SaveAction saveAction;

    @Inject
    private SaveAllAction saveAllAction;

    @Inject
    private ShowPreferencesAction showPreferencesAction;

    @Inject
    private FindActionAction findActionAction;

    @Inject
    private FindReplaceAction findReplaceAction;

    @Inject
    private NavigateToFileAction navigateToFileAction;

    @Inject
    @MainToolbar
    private ToolbarPresenter toolbarPresenter;

    @Inject
    private CutAction cutAction;

    @Inject
    private CopyAction copyAction;

    @Inject
    private PasteAction pasteAction;

    @Inject
    private DeleteItemAction deleteItemAction;

    @Inject
    private RenameItemAction renameItemAction;

    @Inject
    private CollapseAllAction collapseAllAction;

    @Inject
    private ExpandAllAction expandAllAction;

    @Inject
    private OpenProjectAction openProjectAction;

    @Inject
    private CloseProjectAction closeProjectAction;

    @Inject
    private OpenSelectedFileAction openSelectedFileAction;

    @Inject
    private OpenFileAction openFileAction;

    @Inject
    private ExpandNodeAction expandNodeAction;

    @Inject
    private ShowHiddenFilesAction showHiddenFilesAction;

    @Inject
    private FormatterAction formatterAction;

    @Inject
    private UndoAction undoAction;

    @Inject
    private RedoAction redoAction;

    @Inject
    private UploadFileAction uploadFileAction;

    @Inject
    private UploadFolderFromZipAction uploadFolderFromZipAction;

    @Inject
    private DownloadProjectAsZipAction downloadProjectAsZipAction;

    @Inject
    private DownloadItemAction downloadItemAction;

    @Inject
    private ImportProjectFromLocationAction importProjectFromLocationAction;

    @Inject
    private ImportLocalProjectAction importLocalProjectAction;

    @Inject
    private NewProjectAction newProjectAction;

    @Inject
    private CreateModuleAction createModuleAction;

    @Inject
    private NewFolderAction newFolderAction;

    @Inject
    private NewFileAction newFileAction;

    @Inject
    private NewXmlFileAction newXmlFileAction;

    @Inject
    private ImageViewerProvider imageViewerProvider;

    @Inject
    private ProjectConfigurationAction projectConfigurationAction;

    @Inject
    private ExpandEditorAction expandEditorAction;

    @Inject
    private FoldersAlwaysOnTopAction foldersAlwaysOnTopAction;

    @Inject
    private GoIntoAction goIntoAction;

    @Inject
    @Named("XMLFileType")
    private FileType xmlFile;

    @Inject
    @Named("TXTFileType")
    private FileType txtFile;

    @Inject
    @Named("MDFileType")
    private FileType mdFile;

    @Inject
    @Named("PNGFileType")
    private FileType pngFile;

    @Inject
    @Named("BMPFileType")
    private FileType bmpFile;

    @Inject
    @Named("GIFFileType")
    private FileType gifFile;

    @Inject
    @Named("ICOFileType")
    private FileType iconFile;

    @Inject
    @Named("SVGFileType")
    private FileType svgFile;

    @Inject
    @Named("JPEFileType")
    private FileType jpeFile;

    @Inject
    @Named("JPEGFileType")
    private FileType jpegFile;

    @Inject
    @Named("JPGFileType")
    private FileType jpgFile;

    @Inject
    private WsConnectionListener wsConnectionListener;

    /** Instantiates {@link StandardComponentInitializer} an creates standard content. */
    @Inject
    public StandardComponentInitializer(IconRegistry iconRegistry, StandardComponentInitializer.ParserResource parserResource) {
        iconRegistry.registerIcon(new Icon(Constants.BLANK_CATEGORY + ".samples.category.icon", parserResource.samplesCategoryBlank()));
    }

    public void initialize() {
        fileTypeRegistry.registerFileType(xmlFile);

        fileTypeRegistry.registerFileType(txtFile);

        fileTypeRegistry.registerFileType(mdFile);

        fileTypeRegistry.registerFileType(pngFile);
        editorRegistry.registerDefaultEditor(pngFile, imageViewerProvider);

        fileTypeRegistry.registerFileType(bmpFile);
        editorRegistry.registerDefaultEditor(bmpFile, imageViewerProvider);

        fileTypeRegistry.registerFileType(gifFile);
        editorRegistry.registerDefaultEditor(gifFile, imageViewerProvider);

        fileTypeRegistry.registerFileType(iconFile);
        editorRegistry.registerDefaultEditor(iconFile, imageViewerProvider);

        fileTypeRegistry.registerFileType(svgFile);
        editorRegistry.registerDefaultEditor(svgFile, imageViewerProvider);

        fileTypeRegistry.registerFileType(jpeFile);
        editorRegistry.registerDefaultEditor(jpeFile, imageViewerProvider);

        fileTypeRegistry.registerFileType(jpegFile);
        editorRegistry.registerDefaultEditor(jpegFile, imageViewerProvider);

        fileTypeRegistry.registerFileType(jpgFile);
        editorRegistry.registerDefaultEditor(jpgFile, imageViewerProvider);

        // Compose Import Project group
        DefaultActionGroup importProjectGroup = new DefaultActionGroup("Import Project", true, actionManager);
        importProjectGroup.getTemplatePresentation().setSVGIcon(resources.importProject());
        actionManager.registerAction(IdeActions.GROUP_IMPORT_PROJECT, importProjectGroup);
        actionManager.registerAction("importProjectFromLocation", importProjectFromLocationAction);
        actionManager.registerAction("importLocalProjectAction", importLocalProjectAction);
        importProjectGroup.addAction(importProjectFromLocationAction);
        importProjectGroup.addAction(importLocalProjectAction);

        // Compose New group
        DefaultActionGroup newGroup = new DefaultActionGroup("New", true, actionManager);
        newGroup.getTemplatePresentation().setDescription("Create...");
        newGroup.getTemplatePresentation().setSVGIcon(resources.newResource());
        actionManager.registerAction(GROUP_FILE_NEW, newGroup);
        actionManager.registerAction("newProject", newProjectAction);
        actionManager.registerAction("newFile", newFileAction);
        actionManager.registerAction("newFolder", newFolderAction);
        actionManager.registerAction("newXmlFile", newXmlFileAction);
        newXmlFileAction.getTemplatePresentation().setSVGIcon(xmlFile.getSVGImage());

        newGroup.addAction(newProjectAction);
        newGroup.addSeparator();
        newGroup.addAction(newFileAction);
        newGroup.addAction(newFolderAction);
        newGroup.addSeparator();
        newGroup.addAction(newXmlFileAction);

        actionManager.registerAction("uploadFile", uploadFileAction);
        actionManager.registerAction("uploadFolderFromZip", uploadFolderFromZipAction);
        actionManager.registerAction("downloadProjectAsZipAction", downloadProjectAsZipAction);
        actionManager.registerAction("downloadItemAction", downloadItemAction);
        actionManager.registerAction("navigateToFile", navigateToFileAction);
        actionManager.registerAction("projectConfiguration", projectConfigurationAction);
        actionManager.registerAction("createModuleAction", createModuleAction);
        actionManager.registerAction("showHideHiddenFiles", showHiddenFilesAction);

        // Compose Save group
        DefaultActionGroup saveGroup = new DefaultActionGroup(actionManager);
        actionManager.registerAction("saveGroup", saveGroup);
        actionManager.registerAction("save", saveAction);
        actionManager.registerAction("saveAll", saveAllAction);
        saveGroup.addSeparator();
        saveGroup.add(saveAction);
        saveGroup.add(saveAllAction);

        // Compose File menu
        DefaultActionGroup fileGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_FILE);
        fileGroup.add(importProjectGroup);
        fileGroup.add(newGroup);
        fileGroup.add(openProjectAction);
        fileGroup.add(closeProjectAction);
        fileGroup.add(projectConfigurationAction);
        fileGroup.addAction(createModuleAction);
        fileGroup.add(uploadFileAction);
        fileGroup.add(uploadFolderFromZipAction);
        fileGroup.add(downloadProjectAsZipAction);
        fileGroup.add(navigateToFileAction);
        fileGroup.add(showHiddenFilesAction);

        fileGroup.add(cutAction);
        fileGroup.add(copyAction);
        fileGroup.add(pasteAction);

        fileGroup.add(renameItemAction);
        fileGroup.add(deleteItemAction);
        fileGroup.addSeparator();
//        fileGroup.add(expandAllAction);
        fileGroup.add(collapseAllAction);
        fileGroup.addSeparator();
        fileGroup.add(saveGroup);

        // Compose Code menu
        DefaultActionGroup codeGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_CODE);
        actionManager.registerAction("format", formatterAction);
        actionManager.registerAction("undo", undoAction);
        actionManager.registerAction("redo", redoAction);
        codeGroup.add(formatterAction);
        codeGroup.add(undoAction);
        codeGroup.add(redoAction);

        // Compose Window menu
        DefaultActionGroup windowGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_WINDOW);
        actionManager.registerAction("showPreferences", showPreferencesAction);
        windowGroup.add(showPreferencesAction);

        // Compose Help menu
        DefaultActionGroup helpGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_HELP);
        actionManager.registerAction("findActionAction", findActionAction);

        helpGroup.add(findActionAction);
        helpGroup.addSeparator();

        // Compose main context menu
        DefaultActionGroup resourceOperation = new DefaultActionGroup(actionManager);
        actionManager.registerAction("resourceOperation", resourceOperation);
        resourceOperation.addSeparator();
        resourceOperation.add(goIntoAction);
        resourceOperation.add(openSelectedFileAction);

        resourceOperation.add(cutAction);
        resourceOperation.add(copyAction);
        resourceOperation.add(pasteAction);

        resourceOperation.add(renameItemAction);
        resourceOperation.add(deleteItemAction);
        resourceOperation.addSeparator();
//        resourceOperation.add(expandAllAction);
        resourceOperation.add(collapseAllAction);
        resourceOperation.addSeparator();
        resourceOperation.add(downloadItemAction);
        resourceOperation.addSeparator();
        resourceOperation.add(createModuleAction);

        DefaultActionGroup closeProjectGroup = new DefaultActionGroup(actionManager);
        actionManager.registerAction("closeProjectGroup", closeProjectGroup);
        closeProjectGroup.addSeparator();
        closeProjectGroup.add(closeProjectAction);

        DefaultActionGroup mainContextMenuGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_MAIN_CONTEXT_MENU);
        mainContextMenuGroup.add(newGroup);
        mainContextMenuGroup.addSeparator();
        mainContextMenuGroup.add(resourceOperation);
        mainContextMenuGroup.add(closeProjectGroup);

        actionManager.registerAction("expandEditor", expandEditorAction);
        DefaultActionGroup rightMenuGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_RIGHT_MAIN_MENU);
        rightMenuGroup.add(expandEditorAction);

        // Compose main toolbar
        DefaultActionGroup changeResourceGroup = new DefaultActionGroup(actionManager);
        actionManager.registerAction("changeResourceGroup", changeResourceGroup);
        actionManager.registerAction("openProject", openProjectAction);
        actionManager.registerAction("closeProject", closeProjectAction);
        actionManager.registerAction("openSelectedFile", openSelectedFileAction);

        actionManager.registerAction("cut", cutAction);
        actionManager.registerAction("copy", copyAction);
        actionManager.registerAction("paste", pasteAction);

        actionManager.registerAction("renameResource", renameItemAction);
        actionManager.registerAction("deleteItem", deleteItemAction);

        actionManager.registerAction("expandAll", expandAllAction);
        actionManager.registerAction("collapseAll", collapseAllAction);

        actionManager.registerAction("findReplace", findReplaceAction);
        actionManager.registerAction("openFile", openFileAction);
        actionManager.registerAction("expandNode", expandNodeAction);

        changeResourceGroup.add(closeProjectAction);
        changeResourceGroup.add(cutAction);
        changeResourceGroup.add(copyAction);
        changeResourceGroup.add(pasteAction);
        changeResourceGroup.add(deleteItemAction);
        changeResourceGroup.addSeparator();

        DefaultActionGroup mainToolbarGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_MAIN_TOOLBAR);
        mainToolbarGroup.addSeparator();
        mainToolbarGroup.add(newGroup);
        mainToolbarGroup.addSeparator();
        mainToolbarGroup.addSeparator();
        mainToolbarGroup.add(saveAllAction);
        mainToolbarGroup.addSeparator();
        mainToolbarGroup.add(changeResourceGroup);
        toolbarPresenter.bindMainGroup(mainToolbarGroup);

        DefaultActionGroup rightToolbarGroup = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_RIGHT_TOOLBAR);
        toolbarPresenter.bindRightGroup(rightToolbarGroup);

        DefaultActionGroup projectExplorerContextMenu = (DefaultActionGroup)actionManager.getAction(IdeActions.GROUP_PROJECT_EXPLORER_CONTEXT_MENU);
        projectExplorerContextMenu.add(foldersAlwaysOnTopAction);
        actionManager.registerAction("foldersAlwaysOnTop", foldersAlwaysOnTopAction);

        // Define hot-keys
        keyBinding.getGlobal().addKey(new KeyBuilder().action().alt().charCode('n').build(), "navigateToFile");
        keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('A').build(), "findActionAction");
        keyBinding.getGlobal().addKey(new KeyBuilder().alt().charCode('L').build(), "format");

        keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('c').build(), "copy");
        keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('x').build(), "cut");
        keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('v').build(), "paste");
    }

}
