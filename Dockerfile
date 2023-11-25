FROM ubuntu:jammy
COPY target/legacy-server /legacy-server
CMD ["/legacy-server"]