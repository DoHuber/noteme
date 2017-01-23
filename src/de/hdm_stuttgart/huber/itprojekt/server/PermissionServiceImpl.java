package de.hdm_stuttgart.huber.itprojekt.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm_stuttgart.huber.itprojekt.server.db.NoteMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.PermissionMapper;
import de.hdm_stuttgart.huber.itprojekt.server.db.UserInfoMapper;
import de.hdm_stuttgart.huber.itprojekt.shared.PermissionService;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Note;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Permission.Level;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.Shareable;
import de.hdm_stuttgart.huber.itprojekt.shared.domainobjects.UserInfo;

import java.util.Vector;

public class PermissionServiceImpl extends RemoteServiceServlet implements PermissionService {

    /**
     * Das war eclipse
     */
    private static final long serialVersionUID = -1339547511763075795L;

    private PermissionMapper permissionMapper = PermissionMapper.getPermissionMapper();

    public PermissionServiceImpl() {

    }

    public PermissionServiceImpl(Object delegate) {
        super(delegate);

    }

    @Override
    public void shareWith(UserInfo beneficiary, Shareable sharedObject, Level l) throws IllegalArgumentException {

        Permission p = permissionMapper.getPermissionFor(beneficiary, sharedObject);
        if (p == null) {

            p = new Permission();
            p.setLevel(l);
            setCurrentUserAsAuthor(p, beneficiary);
            createNewPermission(p, beneficiary, sharedObject);

        } else {

            upgradeExistingPermissionTo(p, l);

        }

        if (sharedObject.getType() == 'b') {

            Vector<Note> notesToShare = NoteMapper.getNoteMapper().getAllNotesForNoteBookId(sharedObject.getId());
            for (Note row : notesToShare) {
                shareWith(beneficiary, row, l);
            }

        }

    }

    private void setCurrentUserAsAuthor(Permission p, UserInfo beneficiary) {

        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            throw new InvalidLoginStatusException();
        }

        User currentGoogleUser = userService.getCurrentUser();
        UserInfo author = UserInfoMapper.getUserInfoMapper().findUserByGoogleId(currentGoogleUser.getUserId());

        p.setAuthor(author);

    }

    private void createNewPermission(Permission p, UserInfo beneficiary, Shareable sharedObject) {

        p.setBeneficiary(beneficiary);
        p.setSharedObject(sharedObject);
        permissionMapper.createPermission(p);

    }

    private void upgradeExistingPermissionTo(Permission p, Level l) {

        p.setLevel(l);
        permissionMapper.savePermission(p);

    }

    @Override
    public void shareWith(String userEmail, Shareable sharedObject, Level l) {

        UserInfo userToShareWith = UserInfoMapper.getUserInfoMapper().findByEmailAdress(userEmail);
        shareWith(userToShareWith, sharedObject, l);

    }

    @Override
    public Permission getRunTimePermissionFor(UserInfo u, Shareable sharedObject) {

        return permissionMapper.getPermissionFor(u, sharedObject);
    }

    @Override
    public Vector<Permission> getAllPermissionsFor(Shareable s) {

        return permissionMapper.getAllPermissionsFor(s);

    }

    @Override
    public void deletePermission(Permission p) {

        permissionMapper.deletePermission(p);

    }

    @Override
    public Vector<Permission> getAllPermissionsCreatedBy(UserInfo u) {

        return permissionMapper.getAllPermissionsCreatedBy(u);

    }

    @Override
    public Vector<Permission> getAllPermissions() {

        return permissionMapper.getAllPermissions();

    }

}
