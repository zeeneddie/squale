/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airfrance.welcom.outils;

interface IWConfigurator
{
    public static final String MESSAGE_POPUP_STORE_IN_SESSION = "messagePopup.storeInSession";

    public static final String ADDONS_ACCESS_MANAGER_TABLE_ACCESSKEY = "addons.accessManager.table.accesskey";

    public static final String ADDONS_ACCESS_MANAGER_TABLE_PROFILE = "addons.accessManager.table.profile";

    public static final String ADDONS_ACCESS_MANAGER_TABLE_PROFILEACCESSKEYINT =
        "addons.accessManager.table.profileaccesskeyint";

    public static final String ADDONS_MESSAGE_MANAGER_TABLE_LIBELLES = "addons.messageManager.table.libelles";

    public static final String ADDONS_ACCESS_MANAGER_TABLE_PROFILEACCESSKEY =
        "addons.accessManager.table.profileaccesskey";

    public static final String ADDONS_ACCESS_MANAGER_ENABLED = "addons.accessManager.enabled";

    public static final String ADDONS_MESSAGE_MANAGER_ENABLED = "addons.messageManager.enabled";

    public static final String ADDONS_SPELL_MANAGER_ENABLED = "addons.spellManager.enabled";

    public static final String ADDONS_ACCESS_MANAGER_CONFIG_EXCEL_PATH = "addons.accessManager.config.excel.path";

    public static final String ADDONS_TABLE_NAME = "addons.table.name";

    public static final String ADDONS_MESSAGE_MANAGER_LOCALES = "addons.messageManager.locales";

    public static final String ADDONS_MESSAGE_MANAGER_REFRESH_DELAY_MS = "addons.messageManager.refresh.delay.ms";

    public static final String ADDONS_MESSAGE_MANAGER_CLONNAGE_ENABLED = "addons.messageManager.clonnage.enabled";

    public static final String ENCODING_CHARSET = "encoding.charset";

    public static final String DEBUG_CONFIG_JDBC = "debug.config.jdbc";

    public static final String ADDONS_CONFIG_HIBERNATE_PROVIDERPERSISTENCE =
        "addons.config.hibernate.providerpersistence";

    public static final String FONT_PATH = "font.path";

    public static final String CHARTE = "charte";

    public static final String CHECKTIMEOUT_ENABLED = "checktimeout.enabled";

    public static final String EASY_COMPLETE_DEFAULT_MAXIMUM_RETURNED_ELEMENTS =
        "easyComplete.default.maximum.returned.elements";

    public static final String LAZYLOADING_KEY = "optiflux.global.lazyloading";

    public static final String DEFAULT_TIMEZONE = "default.timezone";

    public static final String DEBUG_CONFIG_JVM = "debug.config.jvm";

    public static final String PATH_JSP_SUCCESS = "path.jsp.success";

    public static final String PATH_JSP_PREPARATION_IMPRESSION_PDF = "path.jsp.preparationImpressionPDF";

    public static final String PATH_JSP_APERCU_PDFEXTERNE = "path.jsp.apercuPDFExterne";

    public static final String PATH_JSP_APERCU_PDF = "path.jsp.apercuPDF";

    public static final String ERROR_PAGE_JSP = "error.page.jsp";

    public static final String OPTIFLUX_CLEANSPACES = "optiflux.cleanspaces";

    public static final String OPTIFLUX_AUTORESET_CHECKBOX = "optiflux.autoreset.checkbox";

    public static final String OPTIFLUX_GZIPFILTER_ALLOWEDCONTENTTYPE = "optiflux.gzipfilter.allowedcontenttype";

    public static final String OPTIFLUX_COMPRESSION_MODE = "optiflux.compression.mode";

    public static final String OPTIFLUX_GLOBAL_LAZYLOADING_HISTORY = "optiflux.global.lazyloading.history";

    public static final String OPTIFLUX_JSOBFUSCATOR_DISABLED = "optiflux.jsobfuscator.disabled";

    public static final String OPTIFLUX_REMOVECOMMENTS_JS = "optiflux.removecomments.js";

    public static final String OPTIFLUX_REMOVECOMMENTS_CSS = "optiflux.removecomments.css";

    public static final String OPTIFLUX_COMPRESSION_PREFIX_HTML = "optiflux.compression.prefix.html";

    public static final String OPTIFLUX_COMPRESSION_PREFIX_IMG = "optiflux.compression.prefix.img";

    public static final String OPTIFLUX_GLOBAL_LAZYLOADING_COMBO = "optiflux.global.lazyloading.combo";

    public static final String OPTIFLUX_GLOBAL_LAZYLOADING_DDP = "optiflux.global.lazyloading.ddp";

    public static final String OPTIFLUX_COMPRESSION_PREFIX_JS = "optiflux.compression.prefix.js";

    public static final String OPTIFLUX_GLOBAL_LAZYLOADING_ONGLETS = "optiflux.global.lazyloading.onglets";

    public static final String OPTIFLUX_REDIRECT_CMSINTRANET = "optiflux.redirect.cmsintranet";

    public static final String OPTIFLUX_COMPRESSION_PREFIX_CSS = "optiflux.compression.prefix.css";

    /** clef pour le composant progress bar */
    public static final String BATCH_TASKS_POOLSIZE = "batchtask.pool.size";

    public static final String BATCH_TASKS_MAXLIFEDURATION = "batchtask.pool.maxlifeduration";

    public static final String AIDE_ACTIVE = "aide.active";

    public static final String ACCESS_KEY_USE_LEGACY = "accessKey.use.legacy";

    /** cle pour l'image de la tetiere */
    public final static String HEADER_IMAGE_KEY = "header.tetiere.image";

    /** cle pour l'image du logo AF */
    public final static String HEADER_LOGOAF_KEY = "header.logoaf.image";

    /** cle pour l'image du bayadere */
    public final static String HEADER_BAYADERE_KEY = "header.bayadere.image";

    /** cle pour le path de la css local */
    public final static String HEADER_LOCALCSS_PATH_KEY = "header.local.css.path";

    /** cle pour le path du js local */
    public final static String HEADER_LOCALJS_PATH_KEY = "header.local.js.path";

    public static final String POPUP_IMG_ICON_CLOSE = "popup.img.icon.close";

    public static final String POPUP_IMG_BANDEAU = "popup.img.bandeau";

    public static final String BODY_CANVAS_LEFT_PAGE_INCLUDE = "body.canvasLeft.pageInclude";

    public static final String BODY_CANVAS_HEADER_PAGE_INCLUDE = "body.canvasHeader.pageInclude";

    public static final String EASY_COMPLETE_DEFAULT_DECORATION_CLASS = "easyComplete.default.decoration.class";

    public static final String EASY_COMPLETE_NOTIFIER = "easyComplete.notifier";

    public static final String DATE_USE_LEGACY = "date.use.legacy";

    public static final String CALENDAR_DEFAULT_END_YEAR = "calendar.default.endYear";

    public static final String CALENDAR_DEFAULT_BEGIN_YEAR = "calendar.default.beginYear";

    public static final String GENIAL_FIELD = "genial.field";

    public static final String HTMLAREA_DEFAULT_COLS = "htmlarea.default.cols";

    public static final String HTMLAREA_DEFAULT_ROWS = "htmlarea.default.rows";

    public static final String HTMLAREA_DEFAULT_PATH = "htmlarea.default.path";

    public static final String WELCOM_MENU_LIGHT = "welcom.menu.light";

    public static final String WELCOM_INTERNAL_TABLE_FIRST = "welcom.internal.table.first";

    public static final String WELCOM_INTERNAL_TABLE_PREV = "welcom.internal.table.prev";

    public static final String WELCOM_INTERNAL_TABLE_NEXT = "welcom.internal.table.next";

    public static final String WELCOM_INTERNAL_TABLE_LAST = "welcom.internal.table.last";

    public static final String TABLES_RELOAD_BUTTONS = "tables.reload.buttons";

    public static final String CHARTEV3_MENU_JSURL = "chartev3.menu.jsurl";

    public static final String CHARTEV3_TRACEUR_JSURL = "chartev3.traceur.jsurl";

    public static final String CHARTEV3_ID = "chartev3.id";

    public static final String CHARTEV3_CLASS = "chartev3.class";

    public static final String CHARTEV3_HEADER_CIEL = "chartev3.header.ciel";

    public static final String CHARTEV3_LINK_SPAN_COMPATIBILITY = "chartev3.link.span.compatibility";

    public static final String CHARTEV3_ONGLET_STYLE_SELECTIONNER = "chartev3.onglet.style.selectionner";

    public static final String CHARTEV3_BOUTON_ICONE = "chartev3.bouton.icone";

    public static final String CHARTEV3_BOUTON_BLUEAF = "chartev3.bouton.blueaf";

    public static final String CHARTEV3_BOUTON_WHITE = "chartev3.bouton.white";

    public static final String CHARTEV2_BOUTON_SKIN = "chartev2.bouton.skin";

    public static final String CHARTEV2_ONGLET_STYLE_SELECTIONNER = "chartev2.onglet.style.selectionner";

    public static final String CHARTEV2_ONGLET_STYLE_NON_SELECTIONNER = "chartev2.onglet.style.nonSelectionner";

    public static final String CHARTEV2_ONGLET_STYLE_SELECTIONNER_LEFT = "chartev2.onglet.style.selectionner.left";

    public static final String CHARTEV2_ONGLET_STYLE_NON_SELECTIONNER_LEFT =
        "chartev2.onglet.style.nonSelectionner.left";

    public static final String CHARTEV2_ONGLET_STYLE_SELECTIONNER_CENTER = "chartev2.onglet.style.selectionner.center";

    public static final String CHARTEV2_ONGLET_STYLE_NON_SELECTIONNER_CENTER =
        "chartev2.onglet.style.nonSelectionner.center";

    public static final String CHARTEV2_ONGLET_STYLE_SELECTIONNER_RIGHT = "chartev2.onglet.style.selectionner.right";

    public static final String CHARTEV2_ONGLET_STYLE_NON_SELECTIONNER_RIGHT =
        "chartev2.onglet.style.nonSelectionner.right";

    public static final String CHARTEV2_MENU_JSURL = "chartev2.menu.jsurl";

    public static final String CHARTEV2_FIELD_AST = "chartev2.field.ast";

    public static final String CHARTEV2_FIELD_CLEARPIXEL = "chartev2.field.clearpixel";

    public static final String CHARTEV2_ID = "chartev2.id";

    public static final String CHARTEV2_CLASS = "chartev2.class";

    public static final String CHARTEV2_VERSION = "chartev2.version";

    public static final String CHARTEV2_PIX_GREY_GIF = "chartev2.pix_grey.gif";

    public static final String CHARTEV2_BOUTON_WHITE = "chartev2.bouton.white";

    public static final String CHARTEV2_BOUTON_PATH_FORM = "chartev2.bouton.path.form";

    public static final String CHARTEV2_BOUTON_PATH_MENU = "chartev2.bouton.path.menu";

    public static final String CHARTEV2_BOUTON_FORM_ICONE = "chartev2.bouton.form.icone";

    public static final String CHARTEV2_BOUTON_BLUEAF = "chartev2.bouton.blueaf";

}
