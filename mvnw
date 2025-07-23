#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup script for Unix
# ----------------------------------------------------------------------------

# Required ENV vars:
# ------------------
#   JAVA_HOME - location of a JDK home dir

if [ -z "$MAVEN_OPTS" ] ; then
  export MAVEN_OPTS="-Xms512m -Xmx1024m"
fi

MAVEN_PROJECTBASEDIR=${MAVEN_BASEDIR:-"$BASE_DIR"}
MAVEN_OPTS="$MAVEN_OPTS -classpath $MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"

exec "$JAVACMD" $MAVEN_OPTS \
  -Dmaven.home="$MAVEN_HOME" \
  -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" \
  org.apache.maven.wrapper.MavenWrapperMain "$@"
