package day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DiskDirectory extends DiskEntity {
    private String path;
    private HashSet<DiskEntity> subentities;

    public DiskDirectory(String name, String path) {
        super(name);
        this.path = path;
        this.subentities = new HashSet<DiskEntity>();
    }

    public HashSet<DiskEntity> getSubentities() {
        return this.subentities;
    }

    public String getSuperDirPath() {
        final List<String> splitPath = Arrays.asList(this.path.split("/"));

        if (splitPath.size() == 1) {
            return null;
        }

        if (splitPath.size() == 2) {
            return "/";
        }

        return String.join("/", splitPath.subList(0, splitPath.size() - 1));

    }

    public void showSubentities() {
        this.showSubentities(2);
    }

    public void showSubentities(int numOfSpaces) {
        System.out.println(this.name);
        for (final DiskEntity entity : this.subentities) {
            String spaces = "";
            for (int i = 0; i < numOfSpaces; i++) {
                spaces += " ";
            }
            System.out.print(spaces);

            if (entity instanceof DiskFile) {
                final DiskFile file = (DiskFile) entity;
                System.out.println("(F) " + file.getFullName());
            } else {
                final DiskDirectory dir = (DiskDirectory) entity;
                dir.showSubentities(numOfSpaces + 2);
            }
        }
    }

    public void addSubentity(DiskEntity subentity) {
        this.subentities.add(subentity);
    }

    public long getSize() {
        long sum = 0;
        for (DiskEntity subentity : this.subentities) {
            sum += subentity.getSize();
        }

        return sum;
    }

    public static DiskDirectory parseFromCommands(ArrayList<String> commands) {
        final DiskDirectory root = new DiskDirectory("/", "");
        final HashMap<String, DiskDirectory> dirs = new HashMap<String, DiskDirectory>();
        dirs.put("/", root);
        boolean lsOpen = false;
        DiskDirectory currentDir = root;

        for (String command : commands) {
            final Pattern cdCommand = Pattern.compile("\\$ cd (\\w+|\\.\\.|/)");
            final Matcher cdCommandMatcher = cdCommand.matcher(command);
            final boolean cdFound = cdCommandMatcher.matches();

            if (lsOpen && !cdFound && !command.equals("$ ls")) {
                String[] parts = command.split(" ");

                if (parts[0].equals("dir")) {
                    final String dirName = parts[1];
                    final String dirPath = currentDir.path + "/" + dirName;
                    final DiskDirectory newDir = new DiskDirectory(dirName, dirPath);
                    currentDir.addSubentity(newDir);
                    dirs.put(dirPath, newDir);
                } else {
                    final long size = Long.parseLong(parts[0]);

                    final String[] nameAndExtension = parts[1].split("\\.");
                    final String extension = nameAndExtension.length == 1 ? "" : nameAndExtension[1];

                    final DiskFile newFile = new DiskFile(nameAndExtension[0], extension, size);
                    currentDir.addSubentity(newFile);
                }
            } else {
                lsOpen = false;

                if (cdFound) {
                    switch (cdCommandMatcher.group(1)) {
                        case "..":
                            final String superDir = currentDir.getSuperDirPath();
                            currentDir = dirs.get(superDir);
                            break;
                        case "/":
                            currentDir = root;
                            break;
                        default:
                            final String dirName = cdCommandMatcher.group(1);
                            final String dirPath = currentDir.path + "/" + dirName;

                            if (!dirs.containsKey(dirPath)) {
                                final DiskDirectory newDir = new DiskDirectory(dirName, dirPath);
                                currentDir.addSubentity(newDir);
                                dirs.put(dirPath, newDir);
                            }

                            currentDir = dirs.get(dirPath);
                    }
                } else if (command.equals("$ ls")) {
                    lsOpen = true;
                } else {
                    throw new IllegalArgumentException("Invalid command: " + command);
                }
            }
        }

        return root;
    }
}
