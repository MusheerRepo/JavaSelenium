echo "test running with url '$URL' and suite '$XML_SUITE'"
id
whoami
cd /app

mvn clean test -s setting.xml \
   -DURL="$URL"\
   -Dusername="$USER"\
   -Dpassword="$PASSWORD"\
   -Dxmlsuite="$XML_SUITE"\
   -Dtestrailconfig="$TESTRAIL_CONFIG"\

# save result for further steps
echo $? > /result/result
