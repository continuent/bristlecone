#!/bin/bash

##########################################################################
# NAME:  BUILD_TARBALL
#
# SUMMARY:  Creates the release tarball of Tungsten Replicator from 
#           compiled sources
#
# OVERVIEW: Copies required files in the 'release directory', creates the
#           cluster-home plus manifest files and generates the tarball
#
# USAGE:
#    First check out compile all required sources. The include this file 
#    (source build_tarball.sh) and call 'build_tarball' function. Note 
#    that all variables must be set appropriately prior to calling the 
#    function (see build.sh and config files for more details)
#
##########################################################################



build_tarball() {
  ########################################################################
  # Copy files into the community build.
  ########################################################################
  printHeader "Creating Replicator release"
  reldir=${BUILD_DIR}/${relname}
  if [ -d $reldir ]; then
    echo "### Removing old release directory"
    \rm -rf $reldir
  fi
  
  echo "### Creating release: $reldir"
  mkdir -p $reldir
  
  # Copy everything!
  doCopy bristlecone ./build/dist/bristlecone $reldir
  cp LICENSE $reldir
    
  ########################################################################
  # Create the cluster home.
  ########################################################################
  
  #echo "### Creating cluster home"
  ##cluster_home=$reldir/cluster-home
  #mkdir -p $cluster_home/conf/cluster
	# log directory for cluster-home/bin programs
  #mkdir -p $cluster_home/log
  
  #echo "# Copying cluser-home/conf files"
  #cp -r $extra_cluster_home/conf/* $cluster_home/conf
  
  #echo "# Copying cluser-home bin scripts"
  #cp -r $extra_cluster_home/bin $cluster_home
  
  #echo "# Copying in Ruby configuration libraries"
  #cp -r $extra_cluster_home/lib $cluster_home
  #cp -r $extra_cluster_home/samples $cluster_home
  
  #echo "# Copying in oss-commons libraries"
  #cp -r $jars_commons/* $cluster_home/lib
  #cp -r $lib_commons/* $cluster_home/lib
    
  ########################################################################
  # Create manifest file.
  ########################################################################
  
  manifest=${reldir}/.manifest
  echo "### Creating manifest file: $manifest"
  
  echo "# Build manifest file" >> $manifest
  echo "DATE: `date`" >> $manifest
  echo "RELEASE: ${relname}" >> $manifest
  echo "USER ACCOUNT: ${USER}" >> $manifest
  
  # Hudson environment values.  These will be empty in local builds.
  echo "BUILD_NUMBER: ${BUILD_NUMBER}" >> $manifest
  echo "BUILD_ID: ${BUILD_NUMBER}" >> $manifest
  echo "JOB_NAME: ${JOB_NAME}" >> $manifest
  echo "BUILD_TAG: ${BUILD_TAG}" >> $manifest
  echo "HUDSON_URL: ${HUDSON_URL}" >> $manifest
  echo "SVN_REVISION: ${SVN_REVISION}" >> $manifest
  
  # Local values.
  echo "HOST: `hostname`" >> $manifest
  echo "GIT URL:" >> $manifest
  echo -n "  " >> $manifest
  git config --get remote.origin.url  >> $manifest
    
  echo "GIT Revision:" >> $manifest
  echo -n "  " >> $manifest
  (git rev-parse HEAD) >> $manifest

  ########################################################################
  # Create JSON manifest file.
  ########################################################################

  # Extract revision number from the source control info.
  extractRevision() {
    (git rev-parse HEAD)
  }
  
  manifestJSON=${reldir}/.manifest.json
  echo "### Creating JSON manifest file: $manifestJSON"
  
  # Local details.
  echo    "{" >> $manifestJSON
  echo    "  \"date\": \"`date`\"," >> $manifestJSON
  echo    "  \"product\": \"${product}\"," >> $manifestJSON
  echo    "  \"version\":" >> $manifestJSON
  echo    "  {" >> $manifestJSON
  echo    "    \"major\": ${VERSION_MAJOR}," >> $manifestJSON
  echo    "    \"minor\": ${VERSION_MINOR}," >> $manifestJSON
  echo    "    \"revision\": ${VERSION_REVISION}" >> $manifestJSON
  echo    "  }," >> $manifestJSON
  echo    "  \"userAccount\": \"${USER}\"," >> $manifestJSON
  echo    "  \"host\": \"`hostname`\"," >> $manifestJSON
  
  # Hudson environment values.  These will be empty in local builds.
  echo    "  \"hudson\":" >> $manifestJSON
  echo    "  {" >> $manifestJSON
  echo    "    \"buildNumber\": ${BUILD_NUMBER-null}," >> $manifestJSON
  echo    "    \"buildId\": ${BUILD_NUMBER-null}," >> $manifestJSON
  echo    "    \"jobName\": \"${JOB_NAME}\"," >> $manifestJSON
  echo    "    \"buildTag\": \"${BUILD_TAG}\"," >> $manifestJSON
  echo    "    \"URL\": \"${HUDSON_URL}\"," >> $manifestJSON
  echo    "    \"SVNRevision\": ${SVN_REVISION-null}" >> $manifestJSON
  echo    "  }," >> $manifestJSON

  # SVN details.
  echo    "  \"GIT\":" >> $manifestJSON
  echo    "  {" >> $manifestJSON
  echo    "    \"bristlecone\":" >> $manifestJSON
  echo    "    {" >> $manifestJSON
  echo    "      \"URL\": \"`(git config --get remote.origin.url)`\"," >> $manifestJSON
  echo -n "      \"revision\": " >> $manifestJSON
  echo           "\"`extractRevision`\"" >> $manifestJSON
  echo    "    }" >> $manifestJSON
  echo    "  }" >> $manifestJSON
  echo    "}" >> $manifestJSON
      
  cat $manifest
  
  echo "### Cleaning up left over files"
  # find and delete directories named .svn and any file named *<sed extension>
  find ${reldir} \( -name '.svn' -a -type d -o -name "*$SEDBAK_EXT" \) -exec \rm -rf {} \; > /dev/null 2>&1
  
  ########################################################################
  # Generate tar file.
  ########################################################################
  rel_tgz=${relname}.tar.gz
  echo "### Creating tar file: ${rel_tgz}"
  (cd ${reldir}/..; tar -czf ${rel_tgz} ${relname})
}
