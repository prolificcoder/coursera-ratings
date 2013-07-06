require 'rest-client'

module SauceStorage
  attr_reader :url
  @url = "https://#{SAUCE_USERNAME}:#{SAUCE_ACCESS_KEY}@saucelabs.com/rest/v1/storage/#{SAUCE_USERNAME}"

  def self.upload file_path
    file_name = File.basename file_path
    file = File.new file_path
    local_md5 = Digest::MD5.hexdigest File.read file_path

    self.files.each do |file|
      if file['md5'] == local_md5
        puts 'File already uploaded'
        return true
      end
    end

    url = "#{@url}/#{file_name}?overwrite=true"
    remote_md5 = JSON.parse( RestClient.post url, file, content_type: 'application/octet-stream' )['md5']
    local_md5 == remote_md5
  end

  def self.files
    JSON.parse( RestClient.get @url )['files']
  end
end