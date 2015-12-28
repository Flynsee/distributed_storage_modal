package reseau;

public enum Option {
	UPLOAD, UPDATE, DOWNLOAD, REDIRIGER, DEMANDER_FICHIER, NOUVEAU_FICHIER, NOUVEAU_STOCKAGE, ERROR, DEMANDER_LISTE, APPEND, DELETE, FICHIER_EFFACE, PASSWORD_UTILISATEUR, PASSWORD_STOCKAGE, CONNEXION_ACCEPTEE, CONNEXION_REFUSEE;

	protected byte toByte() {

		switch (this) {

		case DOWNLOAD:
			return 2;
		case UPDATE:
			return 3;
		case REDIRIGER:
			return 4;
		case DEMANDER_FICHIER:
			return 5;
		case NOUVEAU_FICHIER:
			return 6;
		case NOUVEAU_STOCKAGE:
			return 7;
		case UPLOAD:
			return 8;
		case DEMANDER_LISTE:
			return 9;
		case APPEND:
			return 10;
		case DELETE:
			return 11;
		case FICHIER_EFFACE:
			return 12;
		case PASSWORD_UTILISATEUR:
			return 13;
		case PASSWORD_STOCKAGE:
			return 14;
		case CONNEXION_ACCEPTEE:
			return 15;
		case CONNEXION_REFUSEE:
			return 16;
		default:
			return -1;
		}
	}

	protected static Option toOption(byte b) {

		switch (b) {

		case 2:
			return DOWNLOAD;
		case 3:
			return UPDATE;
		case 4:
			return REDIRIGER;
		case 5:
			return DEMANDER_FICHIER;
		case 6:
			return NOUVEAU_FICHIER;
		case 7:
			return NOUVEAU_STOCKAGE;
		case 8:
			return UPLOAD;
		case 9:
			return DEMANDER_LISTE;
		case 10:
			return APPEND;
		case 11:
			return DELETE;
		case 12:
			return FICHIER_EFFACE;
		case 13:
			return PASSWORD_UTILISATEUR;
		case 14:
			return PASSWORD_STOCKAGE;
		case 15:
			return CONNEXION_ACCEPTEE;
		case 16:
			return CONNEXION_REFUSEE;
		default:
			return ERROR;
		}
	}

}
