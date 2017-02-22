
DESCRIPTION = "Sigma-Loader for Sigma-DSP firmware uploads"
DEPENDS = "libconfig libxml2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRCBRANCH = "master"
SRCREV = "${AUTOREV}"
SRC_URI = 	"git://github.com/polyvection/sigma-loader.git;branch=${SRCBRANCH} "
SRC_URI += "file://default.xml"
SRC_URI += "file://sigma-loader.in"

S = "${WORKDIR}/git"

INITSCRIPT_NAME = "sigma-loader"
INITSCRIPT_PARAMS = "defaults 80 10"

do_compile_prepend () {
	cp ${WORKDIR}/git/* ${WORKDIR}/build/
}

do_install_append () {
	install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/build/sigma_loader ${D}${sbindir}/

	install -d ${D}${sysconfdir}/sigma-dsp/firmware
	install -m 0755 ${WORKDIR}/default.xml ${D}${sysconfdir}/sigma-dsp/firmware/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/sigma-loader.in ${D}${sysconfdir}/init.d/sigma-loader
	chmod a+x ${D}${sysconfdir}/init.d/sigma-loader
}

FILES_${PN} += "${sbindir}/sigma_loader"
FILES_${PN} += "${sysconfdir}/sigma-dsp/firmware/default.xml"

inherit autotools pkgconfig update-rc.d

RDEPENDS_${PN} = "initscripts"
CONFFILES_${PN} += "${sysconfdir}/init.d/sigma-loader"


